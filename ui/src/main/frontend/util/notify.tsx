import {Notification} from "@vaadin/react-components";
import {ElementStylingTypes} from "Frontend/constant";
import {Strings} from "Frontend/util/strings";
import {Logs} from "Frontend/util/logs";

export class Notify {
    static LOGGER = new Logs("Notify");

    static success(message: string, ...data: any[]) {
        Notification.show(Strings.format(message, data), {
            position: 'top-end',
            theme: ElementStylingTypes.SUCCESS
        });
        this.LOGGER.debug(message, ...data);
    }

    static error(message: string, ...data: any[]) {
        Notification.show(Strings.format(message, data), {
            position: 'top-end',
            theme: ElementStylingTypes.ERROR
        });
        this.LOGGER.error(message, ...data);
    }
}