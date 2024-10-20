metadata {
    definition (name: "Virtual Text Device", namespace: "snoopy", author: "snoopy") {
        capability "Notification"
        command "setText", ["String"]
        attribute "text", "String"
    }
    preferences {
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
    }
}

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

def installed() {
    log.warn "installed..."
}

def updated() {
    log.info "updated..."
    log.warn "debug logging is: ${logEnable == true}"
    log.warn "description logging is: ${txtEnable == true}"
    if (logEnable) runIn(1800,logsOff)
}

def parse(String description) {
}

def setText(str) {
    def descriptionText = "${device.displayName} text is ${str}"
    if (txtEnable) log.info "${descriptionText}"
    sendEvent(name: "text", value: str, descriptionText: descriptionText, isStateChange: true)
}
