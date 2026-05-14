import {Icon, SideNav, SideNavItem} from "@vaadin/react-components";
import {useEffect, useMemo, useState} from "react";
import {BookmarkEndpoint, MenuService, ParticipantAdminEndpoint, SettingsEndpoint} from "Frontend/generated/endpoints";
import Menu from "Frontend/generated/be/riddler/v1/menu/client/model/Menu";
import {useLocation, useNavigate, useParams} from "react-router";
import ParticipantDetail from "Frontend/generated/be/riddler/v1/participant/client/model/ParticipantDetail";
import Bookmark from "Frontend/generated/be/riddler/v1/settings/model/Bookmark";
import {Button} from "@vaadin/react-components/Button.js";
import {ElementStylingTypes} from "Frontend/constant";
import BookmarkType from "Frontend/generated/be/riddler/v1/settings/model/BookmarkType";
import {useSettingsState} from "Frontend/views/secured/settings-context-provider";

export function SideBar() {
    const [menus, setMenus] = useState<Menu[]>([]);
    const [participant, setParticipant] = useState<ParticipantDetail>();
    const [, setBookmarkTypes] = useState<String[]>([]);
    const {settings, setSettings} = useSettingsState();
    const navigate = useNavigate();
    const location = useLocation();
    const params = useParams();

    const isParticipantDetail = () => {
        return !!(params.id && location.pathname.startsWith('/participants/'));
    };

    useEffect(() => {
        MenuService.menu().then(setMenus);
        BookmarkEndpoint.bookmarkTypes().then(setBookmarkTypes);
    }, []);

    useEffect(() => {
        if (isParticipantDetail()) {
            searchParticipant();
        }
    }, [params.id, location.pathname]);

    const searchParticipant = () => {
        ParticipantAdminEndpoint.findById(params.id!!).then(setParticipant);
    };

    const bookmarksJsonString = JSON.stringify(settings.bookmarks || []);
    const menusJsonString = JSON.stringify(menus);
    const participantName = participant
        ? decodeURIComponent(`${participant.first_name} ${participant.last_name}`)
        : "Loading...";

    const mappedMenuData = useMemo(() => {
        const parsedMenus: Menu[] = JSON.parse(menusJsonString);
        const parsedBookmarks: Bookmark[] = JSON.parse(bookmarksJsonString);

        return parsedMenus.map((menu) => {
            const menuPath = menu.path!!;
            const cleanMenuHash = menuPath.startsWith('/#') ? menuPath.substring(1) : menuPath;
            const routingPath = cleanMenuHash.replace(/^#/, '');
            const filtered = parsedBookmarks.filter((b): b is Bookmark => {
                return b !== null && b !== undefined && b.bookmarkType === menu.bookmark_type;
            });
            let activeChildrenList: Bookmark[] = filtered;
            const currentDetailPath = `/${menu.bookmark_type.toLocaleLowerCase()}/${params.id}`;

            if (!filtered.find(b => b.path === currentDetailPath)) {
                if (params.id && currentDetailPath === location.pathname) {
                    const ephemeralBookmark: Bookmark = {
                        bookmarkType: menu.bookmark_type,
                        path: currentDetailPath,
                        label: participantName
                    };
                    activeChildrenList = [ephemeralBookmark, ...filtered];
                }
            }

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
    }, [menusJsonString, bookmarksJsonString, params.id, location.pathname, location.hash, participantName]);

    const createBookmark = (bookmark: Bookmark) => {
        BookmarkEndpoint.bookmark({
            path: bookmark.path,
            label: bookmark.label,
            bookmarkType: bookmark.bookmarkType
        }).then(settings => {
            setSettings(settings);
        });
    };

    const deleteBookmark = (bookmark: Bookmark) => {
        BookmarkEndpoint.deleteBookmark(bookmark).then(() => {
            SettingsEndpoint.getSettings().then(setSettings);
        });
    };

    const isBookmarked = (path: string | undefined, bookmarkType: BookmarkType): boolean => {
        const currentBookmarks: Bookmark[] = JSON.parse(bookmarksJsonString);
        return currentBookmarks.some(b => b?.path?.startsWith(`/${bookmarkType.toLocaleLowerCase()}/`));
    };

    return (
        <SideNav id="sideNav">
            {mappedMenuData.map(({menu, menuPath, routingPath, isCurrent, shouldBeExpanded, activeChildrenList}) => (
                <SideNavItem
                    key={menuPath}
                    ref={(el) => {
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
                    }}
                    onClick={(e) => {
                        e.preventDefault();
                        navigate(routingPath);
                    }}
                >
                    <Icon icon={menu.icon} slot="prefix"/>
                    {menu.label}

                    {activeChildrenList.map(bookmark => {
                        const isChildCurrent = location.pathname === bookmark.path;

                        return (
                            <SideNavItem
                                key={bookmark.path}
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
                                    navigate(bookmark.path!!);
                                }}
                            >
                                <Icon icon="vaadin:user" slot="prefix"/>
                                {bookmark.label}

                                {isBookmarked(params.id, menu.bookmark_type) ? (
                                    <Button
                                        theme={ElementStylingTypes.TERTIARY_INLINE}
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            deleteBookmark(bookmark);
                                        }}
                                    >
                                        <Icon icon="vaadin:unlock" slot="suffix"/>
                                    </Button>
                                ) : (
                                    <Button
                                        theme={ElementStylingTypes.TERTIARY_INLINE}
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            createBookmark(bookmark);
                                        }}
                                    >
                                        <Icon icon="vaadin:lock" slot="suffix"/>
                                    </Button>
                                )}
                            </SideNavItem>
                        );
                    })}
                </SideNavItem>
            ))}
        </SideNav>
    );
}
