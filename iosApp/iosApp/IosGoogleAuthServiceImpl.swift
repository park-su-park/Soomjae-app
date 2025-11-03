import ComposeApp
import Foundation
import GoogleSignIn  // SPM 패키지
import GoogleSignInSwift
import UIKit

public class IosGoogleAuthServiceImpl: SwiftIosGoogleAuthProtocol {

    public func signInWithScope(
        scope: [String],
        onComplete completion:
            @escaping (
                String?,  // idToken
                String?,  // accessToken
                String?,  // email
                String?,  // name
                String?,  // photoUrl
                String?,  // serverAuthCode
                String?  // error
            ) -> Void
    ) {
        guard let presentingVC = Self.topViewController() else {
            completion(nil, nil, nil, nil, nil, nil, "No presenting view controller")
            return
        }

        // GIDConfiguration은 AppDelegate 등에서 clientID 설정 또는 Info.plist 기반
        if GIDSignIn.sharedInstance.configuration == nil {
            // fallback: Info.plist의 GIDClientID를 자동 로드하도록 Xcode 설정 또는 여기서 직접 주입
            // GIDSignIn.sharedInstance.configuration = GIDConfiguration(clientID: "<YOUR_CLIENT_ID>")
        }

        GIDSignIn.sharedInstance.signIn(
            withPresenting: presentingVC,
            hint: nil,
            additionalScopes: scope
        ) { result, error in
            if let error = error {
                completion(nil, nil, nil, nil, nil, nil, error.localizedDescription)
                return
            }
            guard let result = result else {
                completion(nil, nil, nil, nil, nil, nil, "No result")
                return
            }

            let user = result.user
            let idToken = user.idToken?.tokenString
            let accessToken = user.accessToken.tokenString
            let email = user.profile?.email
            let name = user.profile?.name
            let photoUrl = user.profile?.imageURL(withDimension: 320)?.absoluteString
            let serverAuthCode = result.serverAuthCode

            completion(idToken, accessToken, email, name, photoUrl, serverAuthCode, nil)
        }
    }

    private static func topViewController(
        base: UIViewController? = UIApplication.shared.connectedScenes
            .compactMap { ($0 as? UIWindowScene)?.keyWindow }
            .first?.rootViewController
    ) -> UIViewController? {
        if let nav = base as? UINavigationController {
            return topViewController(base: nav.visibleViewController)
        }
        if let tab = base as? UITabBarController,
            let selected = tab.selectedViewController
        {
            return topViewController(base: selected)
        }
        if let presented = base?.presentedViewController {
            return topViewController(base: presented)
        }
        return base
    }
}
