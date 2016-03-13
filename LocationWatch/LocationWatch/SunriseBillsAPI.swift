//
//  SunriseBillsAPI.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

//
//  SunlightBillAPI.swift
//  TableViewControllerDemo
//
//  Created by Zach Zeleznick on 3/9/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import Foundation

import Alamofire

class SunlightBillAPI {
    /* Has results as main, and want bills */
    var data: [ [String:String] ]! //Array of dictionary
    var complete: Bool = false
    init( bioID: String, completion : JSON -> ()) {
        data = [ [String:String] ]()
        let URL = "http://congress.api.sunlightfoundation.com/bills/search?sponsor_id=\(bioID)&apikey=02f5124ba8c74a6db133f9096d584b5c"
        // print(URL)
        Alamofire.request(.GET, URL).validate().responseJSON { (response) in
            switch response.result {
            case .Success:
                if let value = response.result.value {
                    let json = JSON(value)
                    completion(json)
                }
            case .Failure(let error):
                print(error)
            }
        }
    }
}


