import {Button, Card, HorizontalLayout, Upload, VerticalLayout} from "@vaadin/react-components";
import {ParticipantAdminEndpoint} from "Frontend/generated/endpoints";
// @ts-ignore
import styles from "Frontend/themes/riddler/common.module.css";
import {DynamicBase64Image} from "Frontend/components/ui/image/dynamic-image";
import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
import {useParams} from "react-router";
import {ModalType} from "Frontend/views/secured/participants/participant";

export default function ParticipantProfileDetail(
    {
        participant,
        setParticipant,
        setModalType,
        setOpen
    }:
    {
        participant: Participant,
        setModalType: (modalType: ModalType) => void,
        setOpen: (open: boolean) => void,
        setParticipant: (participant: Participant) => void,
    }
) {
    const params = useParams();

    const photoUploadI18n = {
        dropFiles: {one: 'Drop photo here...', many: 'Drop photos here...'},
        addFiles: {one: 'Upload Profile Photo', many: 'Upload Profile Photos'}
    };

    const cvUploadI18n = {
        dropFiles: {one: 'Drop CV PDF here...', many: 'Drop CV PDFs here...'},
        addFiles: {one: 'Upload CV Document (PDF)', many: 'Upload CV Documents (PDF)'}
    };

    function getCookie(name: string): string | null {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop()?.split(';').shift() || null;
        return null;
    }

    return (
        <VerticalLayout className={styles.full_width_layout} theme="padding" style={{alignItems: 'stretch'}}>
            <Card style={{width: '100%', padding: 'var(--lumo-space-l)', boxShadow: 'var(--lumo-box-shadow-s)'}}>
                <HorizontalLayout theme="spacing-xl"
                                  style={{alignItems: 'flex-start', width: '100%', flexWrap: 'wrap'}}>

                    {/* 1. Left Side Column: Avatar Frame & Upload Trigger */}
                    <VerticalLayout theme="spacing-m" style={{width: 'auto', alignItems: 'center', minWidth: '160px'}}>
                        <div style={{position: 'relative'}}>
                            <DynamicBase64Image
                                rawBase64={participant?.photo}
                                altText={`${participant?.first_name}'s Profile Photo`}
                                width="130"
                                height="130"
                                style={{
                                    borderRadius: '50%', // Round avatar design
                                    border: '3px solid var(--lumo-primary-color-10pct)',
                                    boxShadow: 'var(--lumo-box-shadow-xs)'
                                }}
                            />
                        </div>

                        <Upload
                            accept="image/*"
                            target={`/v1/participants/${params.id!!}/photo`}
                            maxFileSize={20000000}
                            nodrop
                            i18n={photoUploadI18n} // 💡 Applied customized label mapping here
                            onUploadBefore={(e: any) => {
                                const csrfToken = getCookie('XSRF-TOKEN');
                                if (csrfToken) {
                                    e.detail.xhr.setRequestHeader('X-XSRF-TOKEN', csrfToken);
                                }
                            }}
                            onUploadSuccess={() => {
                                ParticipantAdminEndpoint.findById(params.id!!).then(setParticipant);
                            }}
                            style={{scale: '0.9'}}
                        />
                    </VerticalLayout>

                    {/* 2. Right Side Column: Core Details & Documents Section */}
                    <VerticalLayout theme="spacing-m" style={{flexGrow: 1, minWidth: '280px'}}>
                        {/* Title Row Headers */}
                        <VerticalLayout theme="spacing-xs" style={{
                            borderBottom: '1px solid var(--lumo-contrast-10pct)',
                            paddingBottom: 'var(--lumo-space-m)',
                            width: '100%'
                        }}>
                            <h2 style={{
                                margin: 0,
                                fontWeight: 600,
                                color: 'var(--lumo-heading-text-color)',
                                fontSize: 'var(--lumo-font-size-xxl)'
                            }}>
                                {participant?.first_name} {participant?.last_name}
                            </h2>
                            <div style={{
                                color: 'var(--lumo-secondary-text-color)',
                                fontSize: 'var(--lumo-font-size-m)',
                                fontFamily: 'var(--lumo-font-family)'
                            }}>
                                {participant?.email_address}
                            </div>
                        </VerticalLayout>

                        {/* Document Asset Dashboard Area */}
                        <VerticalLayout theme="spacing-s" style={{
                            width: '100%',
                            background: 'var(--lumo-contrast-5pct)',
                            padding: 'var(--lumo-space-m)',
                            borderRadius: 'var(--lumo-base-border-radius)'
                        }}>
                            <div style={{
                                fontSize: 'var(--lumo-font-size-s)',
                                fontWeight: 600,
                                color: 'var(--lumo-secondary-text-color)',
                                textTransform: 'uppercase',
                                letterSpacing: '0.05em'
                            }}>
                                Curriculum Vitae
                            </div>

                            <HorizontalLayout theme="spacing-m" style={{
                                alignItems: 'center',
                                width: '100%',
                                justifyContent: 'space-between',
                                flexWrap: 'wrap',
                                gap: 'var(--lumo-space-s)'
                            }}>
                                {/* Left Action: View Trigger Button */}
                                <HorizontalLayout theme="spacing-s" style={{alignItems: 'center'}}>
                                    <Button
                                        theme="primary"
                                        disabled={!participant?.cv}
                                        onClick={() => {
                                            setModalType('CV');
                                            setOpen(true);
                                        }}
                                    >
                                        View CV Document
                                    </Button>

                                    {/* Small visual validation indicator badge */}
                                    <span style={{
                                        fontSize: 'var(--lumo-font-size-xs)',
                                        fontWeight: 'bold',
                                        padding: '4px 10px',
                                        borderRadius: 'var(--lumo-base-border-radius)',
                                        background: participant?.cv ? 'var(--lumo-success-color-10pct)' : 'var(--lumo-error-color-10pct)',
                                        color: participant?.cv ? 'var(--lumo-success-text-color)' : 'var(--lumo-error-text-color)'
                                    }}>
                                        {participant?.cv ? "AVAILABLE" : "MISSING"}
                                    </span>
                                </HorizontalLayout>

                                {/* Right Action: Document Upload Filter Input */}
                                <Upload
                                    accept="application/pdf"
                                    target={`/v1/participants/${params.id!!}/cv`}
                                    maxFileSize={20000000}
                                    nodrop
                                    i18n={cvUploadI18n} // 💡 Applied customized label mapping here
                                    onUploadBefore={(e: any) => {
                                        const csrfToken = getCookie('XSRF-TOKEN');
                                        if (csrfToken) {
                                            e.detail.xhr.setRequestHeader('X-XSRF-TOKEN', csrfToken);
                                        }
                                    }}
                                    onUploadSuccess={() => {
                                        ParticipantAdminEndpoint.findById(params.id!!).then(setParticipant);
                                    }}
                                    style={{margin: 0}}
                                />
                            </HorizontalLayout>
                        </VerticalLayout>

                    </VerticalLayout>
                </HorizontalLayout>
            </Card>
        </VerticalLayout>
    );
}
