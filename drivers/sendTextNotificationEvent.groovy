/*
 * Send Text Notification Event
 *
 * Calls sendEvent with notification text.
 * Based on `httpGetSwitch.groovy` from hubitat/HubitatPublic
 * 
 */

metadata {
    definition(name: "Send Text Notification Event", namespace: "sigoops", author: "SIGOOPS", importUrl: "https://raw.githubusercontent.com/sigoops/hubitat/main/drivers/sendTextNotificationEvent.groovy") {
        capability "Notification"
    }
}

preferences {
    section("Logs") {
        input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
    }
}

def logsOff() {
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable", [value: "false", type: "bool"])
}

def updated() {
    log.info "updated..."
    log.warn "debug logging is: ${logEnable == true}"
    if (logEnable) runIn(1800, logsOff)
}

def parse(String description) {
    if (logEnable) log.debug(description)
}

def deviceNotification(message) {
    try {
        if (logEnable) log.debug "Sending notification message: ${message}"
        sendEvent(name: "notification", value: message, isStateChange: true)
    } catch (Exception e) {
        log.warn "Call send notification failed: ${e.message}"
    }
}