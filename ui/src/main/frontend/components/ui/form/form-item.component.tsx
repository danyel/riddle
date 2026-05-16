import {HorizontalLayout} from "@vaadin/react-components";
import {ReactNode} from "react";

interface FormItemProperties {
    children?: ReactNode;
}

export default function FormItem({children}: FormItemProperties): ReactNode {
    return (
        <HorizontalLayout style={{
            justifyContent: 'space-between',
            borderBottom: '1px solid var(--lumo-contrast-10pct)',
            paddingBottom: 'var(--lumo-space-s)'
        }}>
            {children}
        </HorizontalLayout>
    );
}