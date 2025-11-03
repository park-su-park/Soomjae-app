import UIKit
import ComposeApp
import GoogleSignIn

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        if let window = window {
            window.rootViewController = MainKt.MainViewController()
            window.makeKeyAndVisible()
        }

        setGoogleSignIn()

        return true
    }

    func application(
        _ app: UIApplication,
        open url: URL,
        options: [UIApplication.OpenURLOptionsKey : Any] = [:]
    ) -> Bool {
        // Try letting Google Sign-In SDK handle the URL
        if GIDSignIn.sharedInstance.handle(url) {
            return true
        }

        // Handle your other custom URLs if needed

        return false
    }

    private func setGoogleSignIn() {
        guard
            let clientID = Bundle.main.object(forInfoDictionaryKey: "GIDClientID") as? String,
            let serverClientId = Bundle.main.object(forInfoDictionaryKey: "GIDServerClientID") as? String
        else {
            assertionFailure("❌ GIDClientID or GIDServerClientID is missing in Info.plist")
            return
        }

        GIDSignIn.sharedInstance.configuration = GIDConfiguration(
            clientID: clientID,
            serverClientID: serverClientId
        )

        IosGoogleAuthServiceKt.setIosAnalyticsProvider(provider: IosGoogleAuthServiceImpl())
    }
}
