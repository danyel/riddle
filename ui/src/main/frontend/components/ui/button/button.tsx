import {Button, ButtonProps} from "@vaadin/react-components/Button.js";
import {ReactNode} from "react";
import {
    BanIcon,
    CheckIcon,
    CloseIcon,
    CrossIcon,
    EyeIcon,
    GlobeIcon,
    PlusIcon,
    SettingsIcon
} from "Frontend/components/ui/icons";
import {ElementStylingTypes} from "Frontend/constant";

/*
primary Recommended for the most important action in a view
tertiary Recommended for lower-importance actions
tertiary-inline Recommended for embedding a Button as part of text content
icon Used to reduce the white space on either side of the icon
small Reduces the button size
large Increases the button size
contrast Recommended as an additional color option
success Recommended as an additional color option
error Recommended for dangerous or irreversible actions
warning Recommended for actions related to warnings
 */


export function CheckButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={CheckIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.PRIMARY}
        />
    );
}

export function CloseButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={CloseIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.PRIMARY_ERROR}
        />
    );
}

export function PlusButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={PlusIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.PRIMARY}
        />
    );
}

export function CancelButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={CrossIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
        />
    );
}

export function ViewDetailButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={EyeIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
        />
    );
}

export function BanButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={BanIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.ERROR}
        />
    );
}

export function GenerateToken(props: { onClick: () => void }) {
    return (
        <><BaseButton
            icon={GlobeIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
        /></>
    );
}

export function SettingsButton(props: { onClick: () => void }) {
    return (
        <BaseButton
            icon={SettingsIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
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