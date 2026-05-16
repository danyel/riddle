import {Icon, SideNav, SideNavItem} from "@vaadin/react-components";
import {useEffect, useMemo, useState} from "react";
import {
    BookmarkEndpoint,
    MenuService,
    ParticipantAdminEndpoint,
    PublicationsEndpoint,
    QuestionEndpoint,
    SettingsEndpoint
} from "Frontend/generated/endpoints";
import Menu from "Frontend/generated/be/riddler/v1/menu/client/model/Menu";
import {useLocation, useNavigate, useParams} from "react-router";
import Participant from "Frontend/generated/be/riddler/v1/participant/client/model/Participant";
import Bookmark from "Frontend/generated/be/riddler/v1/settings/model/Bookmark";
import {Button} from "@vaadin/react-components/Button.js";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import {useSettingsState} from "Frontend/views/secured/settings-context-provider";
import Question from "Frontend/generated/be/riddler/v1/question/client/model/Question";
import {Logs, Notify, Objects, Strings, Urls} from "Frontend/util";
// @ts-ignore
import styles from 'Frontend/themes/riddler/common.module.css';
import Publication from "Frontend/generated/be/riddler/v1/publication/client/model/Publication";

export function SideBar() {
    const [menus, setMenus] = useState<Menu[]>([]);
    const [participant, setParticipant] = useState<Participant>();
    const [question, setQuestion] = useState<Question>();
    const [publication, setPublication] = useState<Publication>();
    const [administration, setAdministration] = useState<string>("");
    const [, setBookmarkTypes] = useState<String[]>([]);
    const {settings, setSettings} = useSettingsState();
    const navigate = useNavigate();
    const location = useLocation();
    const params = useParams();
    const logger = new Logs("SideBar");

    useEffect(() => {
        logger.debug('useEffect: {} {}', params.id, location.pathname);
        MenuService.menu().then(setMenus);
        BookmarkEndpoint.bookmarkTypes().then(setBookmarkTypes);
    }, []);

    useEffect(() => {
        logger.debug('useEffect [params.id, location.pathname]: {} {}', params.id, location.pathname);
        if (isParticipant()) {
            searchParticipant();
        } else {
            setParticipant(undefined);
        }

        if (isQuestionDetail()) {
            searchQuestion();
        } else {
            setQuestion(undefined);
        }

        if (isAdministration()) {
            searchAdministration();
        } else {
            setAdministration(Strings.EMPTY);
        }

        if (isPublication()) {
            searchPublication();
        } else {
            setPublication(undefined);
        }
    }, [params.id, location.pathname]);

    const isParticipant = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.PARTICIPANTS.toLocaleLowerCase()}/`));
    };

    const isQuestionDetail = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.QUESTIONS.toLocaleLowerCase()}/`));
    };

    const isAdministration = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.ADMINISTRATIONS.toLocaleLowerCase()}/`));
    };
    const isPublication = () => {
        return !!(params.id && location.pathname.startsWith(`/${BookmarkType.PUBLICATIONS.toLocaleLowerCase()}/`));
    };

    const searchQuestion = () => {
        logger.debug('Fetching question {}', params.id);
        QuestionEndpoint.get(params.id!!).then(setQuestion);
    };
    const searchPublication = () => {
        logger.debug('Fetching publication {}', params.id);
        PublicationsEndpoint.findPublicationById(params.id!!).then(setPublication);
    };
    const searchParticipant = () => {
        logger.debug('Fetching participant {}', params.id);
        ParticipantAdminEndpoint.findById(params.id!!).then(setParticipant);
    };

    const addBookmark = (activeChildrenList: Bookmark[], bookmarkType: BookmarkType, label: string): Bookmark[] => {
        if (params.id && Urls.isSamePath(location.pathname, Urls.makePath(bookmarkType, params.id))) {
            let bookmark: Bookmark = {
                bookmark_type: bookmarkType,
                path: Urls.makePath(bookmarkType, params.id!!),
                label: label
            };
            let foundBookmark = activeChildrenList.find(e => e.path === bookmark.path);
            if (foundBookmark) {
                bookmark = foundBookmark;
            }
            const tmp = activeChildrenList.filter(e => e.path !== bookmark.path);
            return [bookmark, ...tmp];
        }
        return activeChildrenList;
    };
    const searchAdministration = () => {
        setAdministration('administration');
    }

    const bookmarksJsonString = JSON.stringify(settings.bookmarks || []);
    const menusJsonString = JSON.stringify(menus);
    let label = "";

    if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.PARTICIPANTS, params.id))) {
        label = `${participant?.first_name} ${participant?.last_name}`;
    } else if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.QUESTIONS, params.id))) {
        label = question?.title!!;
    } else if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.ADMINISTRATIONS, params.id))) {
        label = administration;
    } else if (Urls.isSamePath(location.pathname, Urls.makePath(BookmarkType.PUBLICATIONS, params.id))) {
        label = publication?.title!!;
    }

    const mappedMenuData = useMemo(() => {
        const parsedMenus: Menu[] = JSON.parse(menusJsonString);
        const parsedBookmarks: Bookmark[] = JSON.parse(bookmarksJsonString);

        return parsedMenus.map((menu) => {
            const menuPath = menu.path!!;
            const cleanMenuHash = menuPath.startsWith('/#') ? menuPath.substring(1) : menuPath;
            const routingPath = cleanMenuHash.replace(/^#/, '');
            let activeChildrenList: Bookmark[] = parsedBookmarks.filter((b): b is Bookmark => {
                return Objects.isNotNullAndNotUndefined(b) && Objects.isEqual(b.bookmark_type, menu.bookmark_type);
            });

            activeChildrenList = addBookmark(activeChildrenList, menu.bookmark_type, label);
            const isCurrent = (location.hash || '#/') === cleanMenuHash;
            const shouldBeExpanded = activeChildrenList.length > 0;
            return {
                menu,
                menuPath,
                routingPath,
                isCurrent,
                shouldBeExpanded,
                activeChildrenList
            };
        });
    }, [menusJsonString, bookmarksJsonString, params.id, location.pathname, location.hash, label]);

    const createBookmark = (bookmark: Bookmark) => {
        BookmarkEndpoint.bookmark({
            path: bookmark.path,
            label: bookmark.label,
            bookmark_type: bookmark.bookmark_type
        }).then(settings => {
            Notify.success('Bookmark {} created', bookmark.path);
            setSettings(settings);
        });
    };

    const deleteBookmark = (bookmark: Bookmark) => {
        logger.debug('Bookmark {}', bookmark.id);
        BookmarkEndpoint.deleteBookmark(bookmark).then(() => {
            Notify.success('Bookmark {} deleted', bookmark.path)
            SettingsEndpoint.getSettings().then(setSettings);
        });
    };

    const isBookmarked = (path: string | undefined): boolean => {
        const currentBookmarks: Bookmark[] = JSON.parse(bookmarksJsonString);
        return currentBookmarks.some(b => {
            return Urls.isSamePath(b.path, path);
        });
    };

    return (
        <SideNav id="sideNav">
            {mappedMenuData.map(({menu, menuPath, routingPath, isCurrent, shouldBeExpanded, activeChildrenList}) => (
                <SideNavItem
                    key={menuPath}
                    ref={(el) => {
                        if (el) {
                            if (el) {
                                if (isCurrent) {
                                    el.setAttribute('current', '');
                                } else {
                                    el.removeAttribute('current');
                                }
                                if (shouldBeExpanded) {
                                    el.setAttribute('expanded', '');
                                } else {
                                    el.removeAttribute('expanded');
                                }
                            }
                        }
                    }}
                    onClick={(e) => {
                        e.preventDefault();
                        navigate(routingPath);
                    }}
                >
                    <div style={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        width: '100%',
                        minWidth: 0
                    }}>
                        <span onClick={() => navigate(routingPath)} style={{cursor: 'pointer', flexGrow: 1}}>
                            {menu.label}
                        </span>
                    </div>

                    {activeChildrenList.map(childBookmark => {
                        const isChildCurrent = location.pathname === childBookmark.path;
                        const bookmarked = isBookmarked(childBookmark.path);
                        return (
                            <SideNavItem
                                key={childBookmark.path}
                                slot="children"
                                ref={(el) => {
                                    if (el) {
                                        if (isChildCurrent) {
                                            el.setAttribute('current', '');
                                        } else {
                                            el.removeAttribute('current');
                                        }
                                    }
                                }}
                                onClick={(e) => {
                                    e.stopPropagation();
                                    e.preventDefault();
                                    navigate(childBookmark.path!!);
                                }}
                            >
                                <div style={{
                                    display: 'flex',
                                    alignItems: 'center',
                                    justifyContent: 'space-between',
                                    width: '100%',
                                    minWidth: 0,
                                    gap: 'var(--lumo-space-s)'
                                }}>
                                    <div
                                        className={styles.ellipsis_single}
                                        title={childBookmark.label}
                                        onClick={() => navigate(childBookmark.path!!)}
                                        style={{
                                            cursor: 'pointer',
                                            flexGrow: 1,
                                            fontSize: 'var(--lumo-font-size-s)'
                                        }}
                                    >
                                        {childBookmark.label}
                                    </div>
                                    <Button
                                        theme="tertiary icon"
                                        style={{
                                            padding: 0,
                                            minWidth: 'var(--lumo-size-s)',
                                            height: 'var(--lumo-size-s)',
                                            color: bookmarked ? 'var(--lumo-primary-text-color)' : 'var(--lumo-disabled-text-color)'
                                        }}
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            bookmarked ? deleteBookmark(childBookmark) : createBookmark(childBookmark);
                                        }}
                                    >
                                        <Icon icon={`vaadin:${bookmarked ? 'star' : 'star-o'}`}/>
                                    </Button>
                                </div>
                            </SideNavItem>
                        );
                    })}
                </SideNavItem>
            ))}
        </SideNav>
    );
}
