//
//  SunriseCommitteeAPI.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

//
//  SunlightCommitteeAPI.swift
//  TableViewControllerDemo
//
//  Created by Zach Zeleznick on 3/9/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import Foundation
import Alamofire

class SunlightCommitteeAPI {
    /* Has results as main, and want names */
    
    var data: [ [String:String] ]! //Array of dictionary
    var complete: Bool = false
    init( bioID: String, completion : JSON -> ()) {
        data = [ [String:String] ]()
        let URL = "http://congress.api.sunlightfoundation.com/committees?member_ids=\(bioID)&apikey=02f5124ba8c74a6db133f9096d584b5c"
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

