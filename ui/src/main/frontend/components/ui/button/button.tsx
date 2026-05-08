import {Button, ButtonProps} from "@vaadin/react-components/Button.js";
import {ReactNode} from "react";
import {CheckIcon, CloseIcon, CrossIcon, EyeIcon, PlusIcon} from "Frontend/components/ui/icons";

export function CheckButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={CheckIcon()}
            onClick={props.onClick}
            theme="primary"
        />
    );
}

export function CloseButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={CloseIcon()}
            onClick={props.onClick}
            theme="primary error"
        />
    );
}

export function PlusButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={PlusIcon()}
            onClick={props.onClick}
            theme="primary"
        />
    );
}

export function CancelButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={CrossIcon()}
            onClick={props.onClick}
            theme="tertiary"
        />
    );
}

export function ViewDetailButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={EyeIcon()}
            onClick={props.onClick}
            theme="primary_inline"
        />
    );
}

interface BaseButtonProps extends ButtonProps {
    icon: ReactNode;
    onClick: () => void;
}

function BaseButton({icon, children, ...rest}: BaseButtonProps) {
    return (
        <Button {...rest}>
            {icon}
            {children}
        </Button>
    );
}