import {Button, ButtonProps} from "@vaadin/react-components/Button.js";
import {Icon} from "@vaadin/react-components/Icon.js";
import {IconsConstant} from "Frontend/constant/constants";

export function CheckButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={IconsConstant.CHECK}
            onClick={props.onClick}
            theme="primary"
        />
    );
}

export function CloseButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={IconsConstant.CLOSE}
            onClick={props.onClick}
            theme="tertiary"
        />
    );
}

export function CancelButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={IconsConstant.CROSS}
            onClick={props.onClick}
            theme="tertiary"
        />
    );
}

export function ViewDetailButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={IconsConstant.EYE}
            onClick={props.onClick}
            theme="primare_inline"
        />
    );
}

interface BaseButtonProps extends ButtonProps {
    icon: string;
    onClick: () => void;
}

function BaseButton({icon, children, ...rest}: BaseButtonProps) {
    return (
        <Button {...rest}>
            <Icon icon={icon}/>
            {children}
        </Button>
    );
}