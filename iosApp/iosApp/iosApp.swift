import ComposeApp
import GoogleSignIn
import SwiftUI
import UIKit

@main
struct iosApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
