import {Icon} from "@vaadin/react-components";

// CRITICAL: You must import the iconset for the icons to render
import '@vaadin/icons';
import {useEffect, useState} from "react";
import {UiService} from "Frontend/generated/endpoints";
import {AbacusIcon} from "Frontend/components/ui/icons";

export default function IconsView() {
    const [icons, setIcons] = useState<string[]>([]);

    useEffect(() => {
        UiService.icons().then(setIcons);
    }, []);
    return (
        <div>
            <AbacusIcon/>
            {icons.map((iconName) => (
                <div key={iconName}>
                    <Icon icon={iconName}/>
                    <span>{iconName}</span>
                </div>
            ))}
        </div>
    );
}