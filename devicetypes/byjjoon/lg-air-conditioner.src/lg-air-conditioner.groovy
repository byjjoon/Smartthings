/*
 *  LG Air Conditioner
 *
 * Original : https://github.com/fison67/LG-Connector/blob/master/devicetypes/fison67/lg-air-conditioner.src/lg-air-conditioner.groovy
*/
 
import groovy.json.JsonSlurper
import groovy.transform.Field

@Field
AIR_CLEAN_VALUE = [
	0:[val: "@AC_MAIN_AIRCLEAN_OFF_W", str:"OFF"],
    1:[val: "@AC_MAIN_AIRCLEAN_ON_W", str:"ON"]
]

@Field
OPERATION_VALUE = [
	0: [val: "@AC_MAIN_OPERATION_OFF_W", str: "OFF"],
    1: [val: "@AC_MAIN_OPERATION_RIGHT_ON_W", str: "RIGHT ON"],
    256: [val: "@AC_MAIN_OPERATION_LEFT_ON_W", str: "LEFT ON"],
    257: [val: "@AC_MAIN_OPERATION_ALL_ON_W", str: "ALL ON"]
]

@Field
OP_MODE_VALUE = [
	0:[val: "@AC_MAIN_OPERATION_MODE_COOL_W", str: ["EN":"COOL", "KR":"냉방"] ],
    1:[val: "@AC_MAIN_OPERATION_MODE_DRY_W", str: ["EN":"DRY", "KR":"제습"] ],
    2:[val: "@AC_MAIN_OPERATION_MODE_FAN_W", str: ["EN":"FAN", "KR":"팬"] ],
    3:[val: "@AC_MAIN_OPERATION_MODE_AI_W", str: ["EN":"AI", "KR":"인공지능"] ],
    4:[val: "@AC_MAIN_OPERATION_MODE_HEAT_W", str: ["EN":"HEAT", "KR":"열"] ],
    5:[val: "@AC_MAIN_OPERATION_MODE_AIRCLEAN_W", str: ["EN":"AIR CLEAN", "KR":"공기청정"] ],
    6:[val: "@AC_MAIN_OPERATION_MODE_ACO_W", str: ["EN":"ACO", "KR":"ACO"] ],
    7:[val: "@AC_MAIN_OPERATION_MODE_AROMA_W", str: ["EN":"AROMA", "KR":"아로마"] ],
    8:[val: "@AC_MAIN_OPERATION_MODE_ENERGYSAVING_W", str: ["EN":"EVERGY SAVING", "KR":"절전"] ]
]

@Field 
WIND_VALUE = [
    0: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_W", str:"Slow"],
    1: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_W", str:"Slow Low"],
    2: [val:"@AC_MAIN_WIND_STRENGTH_LOW_W", str:"Low"],
    3: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_W", str:"Low Mid"],
    4: [val:"@AC_MAIN_WIND_STRENGTH_MID_W", str:"Mid"],
    5: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_W", str:"Mid High"],
    6: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_W", str:"High"],
    7: [val:"@AC_MAIN_WIND_STRENGTH_POWER_W", str:"Power"],
    8: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_W", str:"Auto"],
    9: [val:"@AC_MAIN_WIND_STRENGTH_LONGPOWER_W", str:"Long Power"],
    10: [val:"@AC_MAIN_WIND_STRENGTH_SHOWER_W", str:"Shower"],
    11: [val:"@AC_MAIN_WIND_STRENGTH_FOREST_W", str:"Forest"],
    12: [val:"@AC_MAIN_WIND_STRENGTH_TURBO_W", str:"Turbo"],
    256: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Slow Low Left & Slow Right"],
    257: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Slow Low Left & Slow Low Right"],
    258: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Slow Low Left & Low Right"],
    259: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Slow Low Left & Low Mid Right"],
    260: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Slow Low Left & Mid Right"],
    261: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Slow Low Left & Mid High Right"],
    262: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"Slow Low Left & High Right"],
    263: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"Slow Low Left & Power Right"],
    264: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Slow Low Left & Auto Right"],
    511: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_LEFT_W", str:"Slow Low Left"],
    512: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Low Left & Slow Right"],
    513: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Low Left & Slow Low Right"],
    514: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Low Left & Low Right"],
    515: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Low Left & Low Mid Right"],
    516: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Low Left & Mid Right"],
    517: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Low Left & Mid High Right"],
    518: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"Low Left & High Right"],
    519: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"Low Left & Power Right"],
    520: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Low Left & Auto Right"],
    767: [val:"@AC_MAIN_WIND_STRENGTH_LOW_LEFT_W", str:"Low Left & Auto Right"],
    768: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Low Mid Left & Slow Right"],
    769: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Low Mid Left & Slow Low Right"],
    770: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Low Mid Left & Low Right"],
    771: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Low Mid Left & Low Mid Right"],
    772: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Low Mid Left & Mid Right"],
    773: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Low Mid Left & Mid High Right"],
    774: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"Low Mid Left & High Right"],
    775: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"Low Mid Left & Power Right"],
    776: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Low Mid Left & Auto Right"],
    1023: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_LEFT_W", str:"Low Mid Left & Auto Right"],
    1024: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Mid Left & Slow Right"],
    1025: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Mid Left & Slow Low Right"],
    1026: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Mid Left & Low Right"],
    1027: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Mid Left & Low Mid Right"],
    1028: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Mid Left & Mid Right"],
    1029: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Mid Left & Mid High Right"],
    1030: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"Mid Left & High Right"],
    1031: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"Mid Left & Power Right"],
    1032: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Mid Left & Auto Right"],
    1279: [val:"@AC_MAIN_WIND_STRENGTH_MID_LEFT_W", str:"Mid Left"],
    1280: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Mid High Left & Slow Right"],
    1281: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Mid High Left & Slow Low Right"],
    1282: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Mid High Left & Low Right"],
    1283: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Mid High Left & Low Mid Right"],
    1284: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Mid High Left & Mid Right Right"],
    1285: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Mid High Left & Mid High Right"],
    1286: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"Mid High Left & High Right"],
    1287: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"Mid High Left & Power Right"],
    1288: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Mid High Left & Auto Right"],
    1535: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_LEFT_W", str:"Mid High Left"],
    1536: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"High Left & Slow Right"],
    1537: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"High Left & Slow Low Right"],
    1538: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"High Left & Low Right"],
    1539: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"High Left & Low Mid Right"],
    1540: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"High Left & Mid Right"],
    1541: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"High Left & Mid High Right"],
    1542: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"High Left & High Right"],
    1543: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"High Left & Power Right"],
    1544: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"High Left & Auto Right"],
    1791: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_LEFT_W", str:"High Left"],
    1792: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Power Left & Slow Right"],
    1793: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Power Left & Slow Low Right"],
    1794: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Power Left & Low Right"],
    1795: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Power Left & Low Mid Right"],
    1796: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Power Left & Mid Right"],
    1797: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Power Left & Mid High Right"],
    1798: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"Power Left & High Right"],
    1799: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"Power Left & Power Right"],
    1800: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Power Left & Auto Right"],
    2047: [val:"@AC_MAIN_WIND_STRENGTH_POWER_LEFT_W", str:"Power Left"],
    2048: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Auto Left & Slow Right"],
    2049: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Auto Left & Slow Low Right"],
    2050: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Auto Left & Low Right"],
    2051: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Auto Left & Low Right"],
    2052: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Auto Left & Mid Right"],
    2053: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Auto Left & Mid High Right"],
    2054: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"Auto Left & High Right"],
    2055: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_POWER_RIGHT_W", str:"Auto Left & Power Right"],
    2056: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W|AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Auto Left & Auto Right"],
    2303: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_LEFT_W", str:"Auto Left"],
    2313: [val:"@AC_MAIN_WIND_STRENGTH_LONGPOWER_LEFT_W|AC_MAIN_WIND_STRENGTH_LONGPOWER_RIGHT_W", str:"Long Power All"],
    2570: [val:"@AC_MAIN_WIND_STRENGTH_SHOWER_LEFT_W|AC_MAIN_WIND_STRENGTH_SHOWER_RIGHT_W", str:"Shower All"],
    2827: [val:"@AC_MAIN_WIND_STRENGTH_FOREST_LEFT_W|AC_MAIN_WIND_STRENGTH_FOREST_RIGHT_W", str:"Forest All"],
    3084: [val:"@AC_MAIN_WIND_STRENGTH_TURBO_LEFT_W|AC_MAIN_WIND_STRENGTH_TURBO_RIGHT_W", str:"Turbo All"],
    65280: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_RIGHT_W", str:"Slow Right"],
    65281: [val:"@AC_MAIN_WIND_STRENGTH_SLOW_LOW_RIGHT_W", str:"Log Right"],
    65282: [val:"@AC_MAIN_WIND_STRENGTH_LOW_RIGHT_W", str:"Low Right"],
    65283: [val:"@AC_MAIN_WIND_STRENGTH_LOW_MID_RIGHT_W", str:"Low Mid Right"],
    65284: [val:"@AC_MAIN_WIND_STRENGTH_MID_RIGHT_W", str:"Mid Right"],
    65285: [val:"@AC_MAIN_WIND_STRENGTH_MID_HIGH_RIGHT_W", str:"Mid Hight Right"],
    65286: [val:"@AC_MAIN_WIND_STRENGTH_HIGH_RIGHT_W", str:"High Right"],
    65287: [val:"@AC_MAIN_WIND_STRENGTH_AUTO_RIGHT_W", str:"Auto Right"]
]

metadata {
	definition (name: "LG Air Conditioner", namespace: "ByJJoon", author: "ByJJoon", ocfDeviceType: "oic.d.airconditioner") {
        capability "Switch"
        capability "Thermostat Cooling Setpoint"
        capability "Thermostat Mode"
        capability "Temperature Measurement"
        capability "Relative Humidity Measurement"

        command "coolMode"
        command "wind", ["number"]
        command "setStatus"
        command "control", ["string", "string"]
        
        attribute "mode", "string"
        attribute "airClean", "string"
        attribute "fanMode", "string"
	}

	simulator {
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

def updated() {
}

def setInfo(String app_url, String address) {
	log.debug "${app_url}, ${address}"
	state.app_url = app_url
    state.id = address
}

def setData(dataList){
	for(data in dataList){
        state[data.id] = data.code
    }
}

def setStatus(data){
	log.debug "Update >> ${data.key} >> ${data.data}"
    def jsonObj = new JsonSlurper().parseText(data.data)
    
    if(jsonObj.Operation){
    	def power = jsonObj.Operation.value == "0" ? "off" : "on"
    	sendEvent(name: "switch", value: power, displayed: true)
        
        if(power == "off"){
            sendEvent(name: "thermostatMode", value: "off", displayed: true)
        }

    }

    if(jsonObj.TempCur){
    	sendEvent(name: "temperature", value: jsonObj.TempCur.value, displayed: true)
    }
    
    if(jsonObj.TempCfg){
    	sendEvent(name: "coolingSetpoint", value: jsonObj.TempCfg.value)
    }
    
    if(jsonObj.SensorHumidity){
    	sendEvent(name: "humidity", value: jsonObj.SensorHumidity.value as int)
    }
    
    if(jsonObj.OpMode){
    	sendEvent(name: "mode", value: OP_MODE_VALUE[jsonObj.OpMode.value as int]["str"]["KR"])
        if(device.currentValue("switch") == "on"){
            switch(jsonObj.OpMode.value as int){
                case 0:
                    sendEvent(name: "thermostatMode", value: "cool")
                    break
                case 1:
                    sendEvent(name: "thermostatMode", value: "cool")
                    break
            }
        }
        else{
            sendEvent(name: "thermostatMode", value: "off")
        }
    }
    
    if(jsonObj.AirClean){
    	sendEvent(name: "airClean", value: AIR_CLEAN_VALUE[jsonObj.AirClean.value as int]["str"])
    }
    
    if(jsonObj.WindStrength){
    	def val = WIND_VALUE[jsonObj.WindStrength.value as int]["str"]
    	sendEvent(name: "fanMode", value: WIND_VALUE[jsonObj.WindStrength.value as int]["str"])
    }
    
    updateLastTime();
}


def installed(){
	sendEvent(name: "supportedThermostatModes", value:["cool", "off"])
}


def updateLastTime(){
	def now = new Date().format("yyyy-MM-dd HH:mm:ss", location.timeZone)
    sendEvent(name: "lastCheckin", value: now, displayed:false)
}


def setThermostatMode(mode){
    log.debug "thermostatMode " + mode

    switch(mode){
        case "cool":
            coolMode()
            break
        case "off":
            off()
            break
    }
}


def setFanMode(mode){
    log.debug "setFanMode " + mode

    switch(mode){
        case "Low":
            sendEvent(name: "fanMode", value: "low", displayed: true)
            wind(2)
            break
        case "Mid":
            sendEvent(name: "fanMode", value: "medium", displayed: true)
            wind(4)
            break
        case "High":
            sendEvent(name: "fanMode", value: "high", displayed: true)
            wind(6)
            break
        case "Power":
            sendEvent(name: "fanMode", value: "turbo", displayed: true)
            wind(7)
            break
    }
}


def setCoolingSetpoint(level){
    if(device.currentValue("ThermostatMode") == "off"){
        log.debug "setCoolingSetpoint >> just power on"
        on()   
    }
    else{
        def targetTemp = level.intValue()
        log.debug "setCoolingSetpoint " + targetTemp
       	sendEvent(name: "coolingSetpoint", value: targetTemp)
	    makeCommand("SetTempCfg", '{"TempCfg":"' + targetTemp + '"}')
    }
}


def on(){
    sendEvent(name: "ThermostatMode", value: "cool")
    makeCommand("SetOperation", "257")
}  
def off(){
   	sendEvent(name: "ThermostatMode", value: "off")
	makeCommand("SetOperation", "0")
}


def airCleanOn(){
	makeCommand("SetAirClean", '{"AirClean":"1"}')
}
def airCleanOff(){
	makeCommand("SetAirClean", '{"AirClean":"0"}')
}


def coolMode(){
	makeCommand("SetOpMode", '{"OpMode":"0"}')
}


def wind(val){
	makeCommand("SetWindStrength", '{"WindStrength":"' + val + '"}')
}


def control(cmd, value){
	makeCommand(cmd, value)
}


def makeCommand(command, value){
    def body = [
        "id": state.id,
        "command": command,
        "value": value
    ]
    log.debug body
    def options = _makeCommand(body)
    sendCommand(options, null)
}


def _makeCommand(body){
	def options = [
     	"method": "POST",
        "path": "/devices/control",
        "headers": [
        	"HOST": state.app_url,
            "Content-Type": "application/json"
        ],
        "body":body
    ]
    return options
}


def sendCommand(options, _callback){
	def myhubAction = new physicalgraph.device.HubAction(options, null, [callback: _callback])
    sendHubCommand(myhubAction)
}