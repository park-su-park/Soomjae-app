import ComposeApp
import FirebaseCore
import FirebaseMessaging
import GoogleSignIn
import UIKit
import UserNotifications

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        FirebaseApp.configure()

        UNUserNotificationCenter.current().delegate = self
        Messaging.messaging().delegate = self

        setGoogleSignIn()

        return true
    }

    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        Messaging.messaging().apnsToken = deviceToken

        refreshToken()
    }

    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("iOS: Failed to register for push notifications: \(error.localizedDescription)")
    }

    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        guard let token = fcmToken, !token.isEmpty else {
            refreshToken()
            return
        }

        UserDefaults.standard.set(token, forKey: "FCM_TOKEN")
        IosDeviceTokenHolderBridge.shared.updateToken(token: token)
    }

    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable: Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        Messaging.messaging().appDidReceiveMessage(userInfo)
        completionHandler(.newData)
    }

    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        completionHandler([.banner])
    }

    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        let userInfo = response.notification.request.content.userInfo

        if let chatId = userInfo["chatId"] as? String {
            let deepLinkUrl = "chirp://chat_detail/\(chatId)"
            // TODO: Implement deeplink handler first
            // ExternalUriHandler.shared.onNewUri(uri: deepLinkUrl)
        }

        completionHandler()
    }

    func refreshToken() {
        Task {
            do {
                let fcmToken = try await Messaging.messaging().token()

                UserDefaults.standard.set(fcmToken, forKey: "FCM_TOKEN")
                IosDeviceTokenHolderBridge.shared.updateToken(token: fcmToken)
            } catch {
                print("ios: Error getting FCM token: \(error.localizedDescription)")
            }
        }
    }

    func application(
        _ app: UIApplication,
        open url: URL,
        options: [UIApplication.OpenURLOptionsKey: Any] = [:]
    ) -> Bool {
        if GIDSignIn.sharedInstance.handle(url) {
            return true
        }

        return false
    }

    private func setGoogleSignIn() {
        guard
            let clientID = Bundle.main.object(forInfoDictionaryKey: "GIDClientID") as? String,
            let serverClientId = Bundle.main.object(forInfoDictionaryKey: "GIDServerClientID")
                as? String
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
