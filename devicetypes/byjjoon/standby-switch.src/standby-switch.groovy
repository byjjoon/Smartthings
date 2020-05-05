/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

metadata {
    definition (name: "Standby Switch", namespace: "ByJJoon", author: "ByJJoon", vid: "generic-switch") {
        capability "Switch"
    }
    
    preferences {
        input "ip", "text", type: "text", title: "IP:PORT", description: "enter address must be [ip]:[port]", required: true
        input "switch_num", "text", type: "text", title: "Switch Number", description: "switch1 ~ switch13", required: true
    }
}

def installed(){
    log.debug "installed()"
    init()
}

def uninstalled(){
    log.debug "uninstalled()"
    unschedule()
}

def updated(){
    log.debug "updated()"
    unschedule()
    init()
}

def init(){
    refresh()
    runEvery1Minute(refresh)
}

def refresh(){
    //log.debug "refresh()"

    if(ip){
        def options = [
                "method": "GET",
                "path": "/Switch?switch=" + switch_num,
                "headers": ["HOST": "${ip}"]
        ]

        def myhubAction = new physicalgraph.device.HubAction(options, null, [callback: update_data])
        sendHubCommand(myhubAction)
    }
    else log.error "설정에 IP 주소를 입력하세요!"
}

def update_data(physicalgraph.device.HubResponse hubResponse){
    try {
        def msg = parseLanMessage(hubResponse.description)
        def resp = msg.body
        /*
        'switch1'                                  # 거실1
        'switch2'                                  # 거실2        
        'switch3'                                  # 침실1-1
        'switch4'                                  # 침실1-2
        'switch5'                                  # 침실2-1
        'switch6'                                  # 침실2-2
        'switch7'                                  # 침실3-1
        'switch8'                                  # 침실3-2
        'switch9'                                  # 침실4-1
        'switch10'                                 # 침실4-2
        'switch11'                                 # 주방1
        'switch12'                                 # 주방2
        'switch13'                                 # 주방3
        */
        if(resp == 'on') {
            sendEvent(name: "switch", value: "on")
            //log.debug "대기전력스위치 상태 : 켜짐"
        }
        else{
            sendEvent(name: "switch", value: "off")
            //log.debug "대기전력스위치 상태 : 꺼짐"
        }

    } catch (e) {
        log.error "Exception caught while parsing data: "+e;
    }
}

def on(){
    def options = [
            "method": "GET",
            "path": "/Control_Switch?switch=" + switch_num + "&action=on",
            "headers": ["HOST": "${ip}"]
    ]

    def myhubAction = new physicalgraph.device.HubAction(options, null)
    sendHubCommand(myhubAction)
    sendEvent(name: "switch", value: "on")
    log.debug "대기전력스위치 : ON"
}

def off(){
    def options = [
            "method": "GET",
            "path": "/Control_Switch?switch=" + switch_num + "&action=off",
            "headers": ["HOST": "${ip}"]
    ]

    def myhubAction = new physicalgraph.device.HubAction(options, null)
    sendHubCommand(myhubAction)
    sendEvent(name: "switch", value: "off")
    log.debug "대기전력스위치 : OFF"
}