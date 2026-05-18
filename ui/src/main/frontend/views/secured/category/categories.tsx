import {useEffect, useState} from "react";
import {Button, Dialog, Grid, GridColumn, HorizontalLayout, TextField, VerticalLayout} from "@vaadin/react-components";
import {Notify} from "Frontend/util";
import {CategoryEndpoint} from "Frontend/generated/endpoints";
import Category from "Frontend/generated/be/riddler/v1/category/client/model/Category";
import CreateCategory from "Frontend/generated/be/riddler/v1/category/client/model/CreateCategory";
import {useAuth} from "Frontend/auth";
import UpdateCategory from "Frontend/generated/be/riddler/v1/category/client/model/UpdateCategory";

export default function CategoryManagementPage() {
    const {state} = useAuth();
    const [categories, setCategories] = useState<Category[]>([]);
    const [dialogOpen, setDialogOpen] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState<Category | null>(null);
    const [categoryName, setCategoryName] = useState("");
    const [rawKeywords, setRawKeywords] = useState("");
    const loadData = () => {
        if (state.user) {
            CategoryEndpoint.findAll().then(setCategories);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const openEditor = (category: Category | null) => {
        setSelectedCategory(category);
        if (category) {
            setCategoryName(category.name || "");
            const keywordsString = category.keywords.map(k => k.word).join(", ") || "";
            setRawKeywords(keywordsString);
        } else {
            setCategoryName("");
            setRawKeywords("");
        }
        setDialogOpen(true);
    };

    const handleSave = async () => {
        if (!categoryName.trim()) {
            Notify.error("Category name cannot be left blank.");
            return;
        }

        const keywordList: { id?: string, word: string }[] = rawKeywords.split(",")
            .filter(k => k.length > 0)
            .map(k => {
                return {
                    word: k.trim()
                };
            });

        if (selectedCategory) {
            const updateCategory: UpdateCategory = {
                name: categoryName,
                keywords: keywordList
            };

            try {
                await CategoryEndpoint.update(selectedCategory.id, updateCategory);
                Notify.success("Scanning rule configuration stored successfully!");
                setDialogOpen(false);
                loadData();
            } catch (error) {
                Notify.error("Failed to write rules data changes to backend endpoints.");
            }
        } else {
            const categoryPayload: CreateCategory = {
                name: categoryName,
                keywords: keywordList
            };

            try {
                await CategoryEndpoint.create(categoryPayload);
                Notify.success("Scanning rule configuration stored successfully!");
                setDialogOpen(false);
                loadData();
            } catch (error) {
                Notify.error("Failed to write rules data changes to backend endpoints.");
            }
        }
    };

    const handleDelete = async (id: string) => {
        if (confirm("Are you sure you want to permanently delete this classification rule package? This removes it from all existing participants.")) {
            try {
                await CategoryEndpoint.delete(id);
                Notify.success("Classification rules scrubbed successfully.");
                loadData();
            } catch (error) {
                Notify.error("Failed to delete category rule dependency.");
            }
        }
    };

    const actionRenderer = ({item}: { item: Category }) => (
        <HorizontalLayout theme="spacing-s">
            <Button theme="small secondary" onClick={() => openEditor(item)}>Edit</Button>
            <Button theme="small error primary" onClick={() => handleDelete(item.id!!)}>Delete</Button>
        </HorizontalLayout>
    );

    const keywordsBadgeRenderer = ({item}: { item: Category }) => (
        <HorizontalLayout theme="spacing-xs" style={{flexWrap: 'wrap'}}>
            {item.keywords?.map((k, index) => (
                <span
                    key={index}
                    style={{
                        background: 'var(--lumo-contrast-10pct)',
                        fontSize: 'var(--lumo-font-size-xs)',
                        padding: '2px 6px',
                        borderRadius: '4px',
                        fontFamily: 'var(--lumo-font-family)'
                    }}
                >
                    {k.word}
                </span>
            )) || <span style={{color: 'var(--lumo-disabled-text-color)', fontStyle: 'italic'}}>None</span>}
        </HorizontalLayout>
    );

    return (
        <VerticalLayout theme="padding spacing" style={{width: '100%', alignItems: 'stretch'}}>
            <HorizontalLayout style={{justifyContent: 'space-between', alignItems: 'center', width: '100%'}}>
                <div>
                    <h1 style={{margin: 0, fontSize: 'var(--lumo-font-size-xxl)'}}>CV Category Rules Management</h1>
                    <div style={{color: 'var(--lumo-secondary-text-color)', fontSize: 'var(--lumo-font-size-s)'}}>
                        Define target profile classification tracks and system text scanning keyword anchors
                        dynamically.
                    </div>
                </div>
                <Button theme="primary" onClick={() => openEditor(null)}>Add New Rule</Button>
            </HorizontalLayout>

            {categories.length > 0 ? (
                <Grid items={categories} allRowsVisible={true}>
                    <GridColumn path="name" header="Category Skill Track Name" width="220px" flexGrow={0}/>
                    <GridColumn header="Target Scanning Keywords (Matches 2 or more to activate)"
                                renderer={keywordsBadgeRenderer}/>
                    <GridColumn header="Actions" renderer={actionRenderer} width="160px" flexGrow={0}/>
                </Grid>
            ) : (
                <VerticalLayout
                    theme="padding spacing"
                    style={{
                        alignItems: 'center',
                        justifyContent: 'center',
                        height: '250px',
                        background: 'var(--lumo-contrast-5pct)',
                        borderRadius: 'var(--lumo-base-border-radius)',
                        border: '1px dashed var(--lumo-contrast-20pct)'
                    }}
                >
                    <span style={{
                        color: 'var(--lumo-secondary-text-color)',
                        fontSize: 'var(--lumo-font-size-m)',
                        fontWeight: 500
                    }}>
                        No category rules found
                    </span>
                    <span style={{
                        color: 'var(--lumo-disabled-text-color)',
                        fontSize: 'var(--lumo-font-size-s)'
                    }}>
                        Click "Add New Rule" to configure your first parsing track.
                    </span>
                </VerticalLayout>
            )}

            <Dialog
                headerTitle={selectedCategory ? "Edit Skill Classification Rules" : "Create New Skill Classification Rule"}
                opened={dialogOpen}
                onClosed={() => setDialogOpen(false)}
                footer={
                    <HorizontalLayout theme="spacing" style={{width: '100%', justifyContent: 'flex-end'}}>
                        <Button theme="primary" onClick={handleSave}>Save Rule Configuration</Button>
                        <Button theme="tertiary" onClick={() => setDialogOpen(false)}>Cancel</Button>
                    </HorizontalLayout>
                }
            >
                <VerticalLayout theme="spacing" style={{width: '420px', alignItems: 'stretch'}}>
                    <TextField
                        label="Profile Category Name"
                        placeholder="e.g., Python AI Developer"
                        value={categoryName}
                        onChange={(e) => setCategoryName(e.target.value)}
                        style={{width: '100%'}}
                    />
                    <TextField
                        label="Associated Tracking Keywords"
                        placeholder="Separate tags with commas (e.g., python, pytorch, pandas)"
                        value={rawKeywords}
                        onChange={(e) => setRawKeywords(e.target.value)}
                        style={{width: '100%'}}
                    />
                    <div style={{
                        fontSize: 'var(--lumo-font-size-xs)',
                        color: 'var(--lumo-secondary-text-color)',
                        lineHeight: 'var(--lumo-line-height-s)'
                    }}>
                        * Note: When any participant uploads their CV document, the internal indexing framework parses
                        matching words case-insensitively against the list above.
                    </div>
                </VerticalLayout>
            </Dialog>
        </VerticalLayout>
    );
}
