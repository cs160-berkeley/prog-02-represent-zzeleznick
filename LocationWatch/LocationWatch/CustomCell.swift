//
//  CustomCell.swift
//  LocationWatch
//
//  Created by Zach Zeleznick on 3/12/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

//
//  CustomCell.swift
//  TableViewControllerDemo
//
//  Created by Zach Zeleznick on 3/5/16.
//  Copyright © 2016 zzeleznick. All rights reserved.
//

import UIKit

class CustomCell: UITableViewCell {
    
    
    @IBOutlet weak var name: UILabel!
    
    @IBOutlet weak var party: UILabel!
    
    @IBOutlet weak var twitter: UILabel!
    
    @IBOutlet weak var email: UILabel!
    
    @IBOutlet weak var website: UILabel!
    
    @IBOutlet weak var myImage: UIImageView!
    
    var repDict: [String:String]!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
}
