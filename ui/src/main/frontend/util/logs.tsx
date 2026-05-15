import {Logger} from "Frontend/generated/endpoints";

export class Logs {
    static DEBUG: boolean = true;
    static TEMPLATE: string = "{} {}";

    private static format(message: string, data: any[]): string {
        let index = 0;
        return message.replace(/{}/g, () => {
            if (index < data.length) {
                const item = data[index++];
                return typeof item === 'object' ? JSON.stringify(item) : item;
            }
            return '{}';
        });
    }

    static debug(component: string, message: string, ...data: any[]) {
        if (this.DEBUG) {
            console.log(this.format(this.TEMPLATE, [component, this.format(message, data)]));
            Logger.debug(this.format(this.TEMPLATE, [component, message]), data)
                .then();
        }
    }

    static warn(component: string, message: string, ...data: any[]) {
        if (this.DEBUG) {
            console.warn(this.format(this.TEMPLATE, [component, this.format(message, data)]));
            Logger.warn(this.format(this.TEMPLATE, [component, message]), data)
                .then();
        }
    }

    static info(component: string, message: string, ...data: any[]) {
        console.info(this.format(this.TEMPLATE, [component, this.format(message, data)]));
        Logger.info(this.format(this.TEMPLATE, [component, message]), data)
            .then();
    }

    static error(component: string, message: string, ...data: any[]) {
        console.error(this.format(this.TEMPLATE, [component, this.format(message, data)]));
        Logger.error(this.format(this.TEMPLATE, [component, message]), data)
            .then();
    }
}