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
    definition (name: "Open Lobby", namespace: "ByJJoon", author: "ByJJoon", vid: "generic-contact-4", mnmn: "SmartThings") {
        capability "Door Control"
        capability "Contact Sensor"
    }

    preferences {
        input "ip", "text", type: "text", title: "IP:PORT", description: "enter address must be [ip]:[port]", required: true
        input "floor", "text", type: "text", title: "ROOM", description: "9004, 9014, 9024", required: true
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
    log.debug "refresh()"
    finishClosing()
    
    if(!ip){
        log.error "설정에 IP 주소를 입력하세요!"
    }

}

def open_lobby(){
    def options = [
            "method": "GET",
            "path": "/Open_Lobby?floor=" + floor,
            "headers": ["HOST": "${ip}"]
    ]

    def myhubAction = new physicalgraph.device.HubAction(options, null)
    sendHubCommand(myhubAction)
}


def open() {
    sendEvent(name: "door", value: "closing")
    runIn(6, finishClosing)
    log.debug "open()"
}

def close() {
    open_lobby()
    sendEvent(name: "door", value: "opening")
    runIn(6, finishOpening)
    log.debug "close()"
}

def finishOpening() {
    sendEvent(name: "door", value: "open")
    sendEvent(name: "contact", value: "open")
}

def finishClosing() {
    sendEvent(name: "door", value: "closed")
    sendEvent(name: "contact", value: "closed")
}