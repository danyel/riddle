import {VerticalLayout} from "@vaadin/react-components";
import {useEffect, useState} from "react";
import {useParams} from "react-router";
import {PublicationsEndpoint} from "Frontend/generated/endpoints";
import PublicationDetail from "Frontend/components/publication/publication";
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";

export default function PublicationPage() {
    const [publication, setPublication] = useState<Publication>();
    const [loading, setLoading] = useState(true);
    const params = useParams();

    useEffect(() => {
        if (params.id) {
            getPublication();
        }
    }, [params.id]);

    function getPublication() {
        setLoading(true);
        PublicationsEndpoint.findPublicationById(params.id!!)
            .then((data) => {
                setPublication(data);
            })
            .finally(() => {
                setLoading(false);
            });
    }

    // 💡 Provide an elegant loading container matching your Lumo design tokens
    if (loading) {
        return (
            <VerticalLayout theme="padding spacing"
                            style={{alignItems: 'center', justifyContent: 'center', height: '200px', width: '100%'}}>
                <span style={{
                    color: 'var(--lumo-secondary-text-color)',
                    fontStyle: 'italic',
                    fontSize: 'var(--lumo-font-size-m)'
                }}>
                    Loading publication details...
                </span>
            </VerticalLayout>
        );
    }

    // 💡 Provide a fallback if the ID was invalid or publication wasn't found
    if (!publication) {
        return (
            <VerticalLayout theme="padding spacing"
                            style={{alignItems: 'center', justifyContent: 'center', height: '200px', width: '100%'}}>
                <span style={{color: 'var(--lumo-error-text-color)', fontWeight: 'bold'}}>
                    Publication not found.
                </span>
            </VerticalLayout>
        );
    }

    return (
        <VerticalLayout theme="padding spacing" style={{width: '100%', alignItems: 'stretch'}}>
            <PublicationDetail publication={publication}/>
        </VerticalLayout>
    );
}
