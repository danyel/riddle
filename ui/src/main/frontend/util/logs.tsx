import {Logger} from "Frontend/generated/endpoints";
import {Strings} from "Frontend/util/strings";

export class Logs {
    component: string;
    static DEBUG: boolean = false;
    static TEMPLATE: string = "{} {}";

    constructor(component: string) {
        this.component = component;
        Logger.isDebug()
            .then(e => Logs.DEBUG = e);
    }

    public debug(message: string, ...data: any[]) {
        if (Logs.DEBUG) {
            console.log(Strings.format(Logs.TEMPLATE, [this.component, Strings.format(message, data)]));
            Logger.debug(Strings.format(Logs.TEMPLATE, [this.component, message]), data)
                .then();
        }
    }

    public warn(message: string, ...data: any[]) {
        if (Logs.DEBUG) {
            console.warn(Strings.format(Logs.TEMPLATE, [this.component, Strings.format(message, data)]));
            Logger.warn(Strings.format(Logs.TEMPLATE, [this.component, message]), data)
                .then();
        }
    }

    public info(message: string, ...data: any[]) {
        console.info(Strings.format(Logs.TEMPLATE, [this.component, Strings.format(message, data)]));
        Logger.info(Strings.format(Logs.TEMPLATE, [this.component, message]), data)
            .then();
    }

    public error(message: string, ...data: any[]) {
        console.error(Strings.format(Logs.TEMPLATE, [this.component, Strings.format(message, data)]));
        Logger.error(Strings.format(Logs.TEMPLATE, [this.component, message]), data)
            .then();
    }
}

export const LOGGER = new Logs('Riddler');