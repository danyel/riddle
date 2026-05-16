import {Button, ButtonProps} from "@vaadin/react-components/Button.js";
import {ReactNode} from "react";
import {
    BanIcon,
    CheckIcon,
    CloseIcon,
    EyeIcon,
    KeyIcon,
    NewsPaperIcon,
    PlusIcon,
    RotateLeftIcon
} from "Frontend/components/ui/icons";
import {ElementStylingTypes} from "Frontend/constant";

export function CheckButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={CheckIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.PRIMARY}
            disabled={props.disabled}
        />
    );
}

export function CloseButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={CloseIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.PRIMARY_ERROR}
            disabled={props.disabled}
        />
    );
}

export function PlusButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={PlusIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.PRIMARY}
            disabled={props.disabled}
        />
    );
}

export function CancelButton(props: ButtonDecorationProps, theme: string = ElementStylingTypes.TERTIARY_INLINE) {
    return (
        <BaseButton
            icon={CloseIcon()}
            onClick={props.onClick}
            theme={props.theme}
            disabled={props.disabled}
        />
    );
}

export function ViewDetailButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={EyeIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

export function NewsPaperButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={NewsPaperIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

export function BanButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={BanIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.ERROR}
            disabled={props.disabled}
        />
    );
}

export function GenerateToken(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={KeyIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

export function RefreshButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={RotateLeftIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

export function DeleteButton(props: ButtonDecorationProps) {
    return (
        <BaseButton
            icon={CloseIcon()}
            onClick={props.onClick}
            theme={ElementStylingTypes.TERTIARY_INLINE}
            disabled={props.disabled}
        />
    );
}

interface ButtonDecorationProps extends ButtonProps {
    onClick: () => void;
    disabled?: boolean;
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