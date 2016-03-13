//
//  States.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

//
//  District.swift
//  WatchKitJSON
//
//  Created by Zach Zeleznick on 3/10/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

/*
var states = StateHelper.generateStates()
var total = states.map {$0.districts}
total.reduce(0) { (accumulate, current) in
return accumulate + current }
*/
// 428 returned
// 7 counts of "0" for at-large districts
// additional "delegates" for territories
// http://clerk.house.gov/member_info/olm112.aspx

class StateHelper {
    static let stateCount = 50
    static let repCount = 435
    class func generateStates() -> [State] {
        var out: [State] = []
        for i in (0...self.stateCount-1) {
            out.append(State(rawValue: i)!)
        }
        return out
    }
}

enum State:Int {
    case AL = 0, AK, AZ, AR, CA, CO, CT, DE, FL, GA, HI, ID, IL, IN, IA, KS, KY, LA, ME, MD, MA, MI, MN, MS, MO, MT, NE, NV, NH, NJ, NM, NY, NC, ND, OH, OK, OR, PA, RI, SC, SD, TN, TX, UT, VT, VA, WA, WV, WI, WY
    
    var abbrev:String {
        switch self {
        case AL: return "AL"
        case AK: return "AK"
        case AZ: return "AZ"
        case AR: return "AR"
        case CA: return "CA"
        case CO: return "CO"
        case CT: return "CT"
        case DE: return "DE"
        case FL: return "FL"
        case GA: return "GA"
        case HI: return "HI"
        case ID: return "ID"
        case IL: return "IL"
        case IN: return "IN"
        case IA: return "IA"
        case KS: return "KS"
        case KY: return "KY"
        case LA: return "LA"
        case ME: return "ME"
        case MD: return "MD"
        case MA: return "MA"
        case MI: return "MI"
        case MN: return "MN"
        case MS: return "MS"
        case MO: return "MO"
        case MT: return "MT"
        case NE: return "NE"
        case NV: return "NV"
        case NH: return "NH"
        case NJ: return "NJ"
        case NM: return "NM"
        case NY: return "NY"
        case NC: return "NC"
        case ND: return "ND"
        case OH: return "OH"
        case OK: return "OK"
        case OR: return "OR"
        case PA: return "PA"
        case RI: return "RI"
        case SC: return "SC"
        case SD: return "SD"
        case TN: return "TN"
        case TX: return "TX"
        case UT: return "UT"
        case VT: return "VT"
        case VA: return "VA"
        case WA: return "WA"
        case WV: return "WV"
        case WI: return "WI"
        case WY: return "WY"
        }
    }
    var name:String {
        switch self {
        case AL: return "Alabama"
        case AK: return "Alaska"
        case AZ: return "Arizona"
        case AR: return "Arkansas"
        case CA: return "California"
        case CO: return "Colorado"
        case CT: return "Connecticut"
        case DE: return "Delaware"
        case FL: return "Florida"
        case GA: return "Georgia"
        case HI: return "Hawaii"
        case ID: return "Idaho"
        case IL: return "Illinois"
        case IN: return "Indiana"
        case IA: return "Iowa"
        case KS: return "Kansas"
        case KY: return "Kentucky"
        case LA: return "Louisiana"
        case ME: return "Maine"
        case MD: return "Maryland"
        case MA: return "Massachusetts"
        case MI: return "Michigan"
        case MN: return "Minnesota"
        case MS: return "Mississippi"
        case MO: return "Missouri"
        case MT: return "Montana"
        case NE: return "Nebraska"
        case NV: return "Nevada"
        case NH: return "New Hampshire"
        case NJ: return "New Jersey"
        case NM: return "New Mexico"
        case NY: return "New York"
        case NC: return "North Carolina"
        case ND: return "North Dakota"
        case OH: return "Ohio"
        case OK: return "Oklahoma"
        case OR: return "Oregon"
        case PA: return "Pennsylvania"
        case RI: return "Rhode Island"
        case SC: return "South Carolina"
        case SD: return "South Dakota"
        case TN: return "Tennessee"
        case TX: return "Texas"
        case UT: return "Utah"
        case VT: return "Vermont"
        case VA: return "Virginia"
        case WA: return "Washington"
        case WV: return "West Virginia"
        case WI: return "Wisconsin"
        case WY: return "Wyoming"
        }
    }
    var districtsCount:Int {
        switch self {
        case AL: return 7
        case AK: return 0
        case AZ: return 9
        case AR: return 4
        case CA: return 53
        case CO: return 7
        case CT: return 5
        case DE: return 0
        case FL: return 27
        case GA: return 14
        case HI: return 2
        case ID: return 2
        case IL: return 18
        case IN: return 9
        case IA: return 4
        case KS: return 4
        case KY: return 6
        case LA: return 6
        case ME: return 2
        case MD: return 8
        case MA: return 9
        case MI: return 14
        case MN: return 8
        case MS: return 4
        case MO: return 8
        case MT: return 0
        case NE: return 3
        case NV: return 4
        case NH: return 2
        case NJ: return 12
        case NM: return 3
        case NY: return 27
        case NC: return 13
        case ND: return 0
        case OH: return 16
        case OK: return 5
        case OR: return 5
        case PA: return 18
        case RI: return 2
        case SC: return 7
        case SD: return 0
        case TN: return 9
        case TX: return 36
        case UT: return 4
        case VT: return 0
        case VA: return 11
        case WA: return 10
        case WV: return 3
        case WI: return 8
        case WY: return 0
        }
    }
}