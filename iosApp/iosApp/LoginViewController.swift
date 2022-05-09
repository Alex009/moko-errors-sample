//
//  LoginViewController.swift
//  iosApp
//
//  Created by Aleksey Mikhailov on 08.05.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import shared

class LoginViewController: UIViewController {
    @IBOutlet private var loginField: UITextField!
    @IBOutlet private var passwordField: UITextField!
    
    private var viewModel: LoginViewModel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = LoginViewModel(
            eventsDispatcher: EventsDispatcher(listener: self)
        )
        viewModel.exceptionHandler.bind(viewController: self)
        
        loginField.bindTextTwoWay(liveData: viewModel.login)
        passwordField.bindTextTwoWay(liveData: viewModel.password)
    }
    
    @IBAction private func onLoginPressed() {
        viewModel.onLoginPressed()
    }
}

extension LoginViewController: LoginViewModelEventsListener {
    func showAlert(title: StringDesc?, message: StringDesc) {
        let alert = UIAlertController(
            title: title?.localized(),
            message: message.localized(),
            preferredStyle: .alert
        )
        alert.addAction(UIAlertAction(title: "Ok", style: .default))
        present(alert, animated: true)
    }
}
