import {Logger} from "Frontend/generated/endpoints";
import {Strings} from "Frontend/util/strings";
import {Notify} from "Frontend/util/notify";

export interface Log {
    debug(message: string, ...data: any[]): void;

    warn(message: string, ...data: any[]): void;

    info(message: string, ...data: any[]): void;

    error(message: string, ...data: any[]): void;
}

class DefaultLog implements Log {
    component: string;
    static DEBUG: boolean = false;
    static TEMPLATE: string = "{} {}";

    constructor(component: string) {
        this.component = component;
        Logger.isDebug()
            .then(e => DefaultLog.DEBUG = e)
            .catch(err => Notify.error('Could not retreive the log level {}', err));
    }

    public debug(message: string, ...data: any[]) {
        if (DefaultLog.DEBUG) {
            console.log(Strings.format(DefaultLog.TEMPLATE, [this.component, Strings.format(message, data)]));
            Logger.debug(Strings.format(DefaultLog.TEMPLATE, [this.component, message]), data)
                .then()
                .catch(err => Notify.error('Could not log the statement {}', err));
        }
    }

    public warn(message: string, ...data: any[]) {
        if (DefaultLog.DEBUG) {
            console.warn(Strings.format(DefaultLog.TEMPLATE, [this.component, Strings.format(message, data)]));
            Logger.warn(Strings.format(DefaultLog.TEMPLATE, [this.component, message]), data)
                .then()
                .catch(err => Notify.error('Could not log the statement {}', err));
        }
    }

    public info(message: string, ...data: any[]) {
        console.info(Strings.format(DefaultLog.TEMPLATE, [this.component, Strings.format(message, data)]));
        Logger.info(Strings.format(DefaultLog.TEMPLATE, [this.component, message]), data)
            .then()
            .catch(err => Notify.error('Could not log the statement {}', err));
    }

    public error(message: string, ...data: any[]) {
        console.error(Strings.format(DefaultLog.TEMPLATE, [this.component, Strings.format(message, data)]));
        Logger.error(Strings.format(DefaultLog.TEMPLATE, [this.component, message]), data)
            .then()
            .catch(err => Notify.error('Could not log the statement {}', err));
    }
}

export const LOGGER: Log = new DefaultLog('Riddler');