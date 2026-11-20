# Project Code Guide

This guide explains how the portfolio pages, camera navigation, and redacted version fit together. The main application uses Spring Boot controllers to select Thymeleaf templates; the redacted pages are separate static HTML files.

## Main Menu

The main menu is the Thymeleaf template in [menu.html](src/main/resources/templates/menu.html), styled by [menu.css](src/main/resources/static/css/menu.css).

- The **Continue** link uses Thymeleaf's `@{/index}` URL and opens the security-camera screen.
- The HTML provides the menu content and overlay layers. CSS supplies the CRT scanlines, static effect, background flicker/distortion, and page fade. These effects do not require JavaScript.
- The `/` and `/menu` routes both show this template; route handling is in [MonitorController.java](src/main/java/controller/MonitorController.java).

## The Index

The camera monitor is [index.html](src/main/resources/templates/index.html), with its layout and animations in [monitor.css](src/main/resources/static/css/monitor.css).

- The HUD shows the night clock, power, and usage. The camera map provides links to the four portfolios and the other camera feeds.
- CAM 1A, 1B, 2A, and 2B open the Josh, Ken, Klarc, and Rey portfolios. CAM 3 through CAM 8 use the `/jumpscare/{cam}` route.
- `th:href` lets Thymeleaf generate application-relative URLs for those links.
- The night clock is a CSS animation; the index page itself has no JavaScript timer.

### Shift and redacted timing

The index has a 120-second meta refresh to `/six-am`. The CSS clock animation also runs for 120 seconds, moving the displayed hours from 12 AM through 6 AM. When the ending page [six-am.html](src/main/resources/templates/six-am.html) loads, its 38-second refresh sends the browser to `/redacted/menu.html`.

The redacted monitor does not have its own countdown. The timed transition into the redacted section comes from the ending page.

## Each Portfolios

All four portfolio links use `/portfolio/{id}`. The controller lowercases the ID, chooses the matching template, and redirects unknown IDs to `/`. Each profile is a separate template rather than a shared template populated from a database.

| Camera | Template | Styles and behavior |
| --- | --- | --- |
| CAM 1A — Josh | [josh.html](src/main/resources/templates/portfolios/josh.html) | Uses [josh.css](src/main/resources/static/css/josh.css) for the dossier layout. The template contains the profile, qualifications, and project cards. |
| CAM 1B — Ken | [ken.html](src/main/resources/templates/portfolios/ken.html) | Uses [ken.css](src/main/resources/static/css/ken.css) for the profile layout and effects. The page includes profile details, skills, projects, the clickable avatar, and the classified-note panel. |
| CAM 2A — Klarc | [klarc.html](src/main/resources/templates/portfolios/klarc.html) | Uses [klarc.css](src/main/resources/static/css/klarc.css) and [klarc-recruitment.js](src/main/resources/static/js/klarc-recruitment.js). JavaScript controls the opening sequence before revealing the dossier. |
| CAM 2B — Rey | [rey.html](src/main/resources/templates/portfolios/rey.html) | Uses [rey.css](src/main/resources/static/css/rey.css) for a newspaper-style profile and story. |

The return links lead back to `/index`. Project and contact links are ordinary HTML links; their destinations are defined in the relevant portfolio template.

## The Other Cameras

The camera map sends CAM 3–8 to `/jumpscare/{cam}`. [MonitorController.java](src/main/java/controller/MonitorController.java) selects the feed content and supplies it to [jumpscare.html](src/main/resources/templates/jumpscare.html).

- **CAM 3**, **CAM 4A**, and **CAM 6** supply a title, message, image, and audio file.
- **CAM 5** supplies a video URL instead of an image. The shared template displays an iframe when `videoUrl` is present; otherwise, it displays the image and optional audio.
- **CAM 7** returns the dedicated [cam7.html](src/main/resources/templates/cam7.html) template. Its CSS stages a transmission and image reveal.
- **CAM 8** supplies the title `CAM 08 - GA201`, the message `Nov 13, 2026, 1PM.`, and the `godhelpus.gif` image.
- An unrecognized camera ID uses the signal-lost title, static image, and audio fallback.

Each feed has a return link to the security room.

## The Redacted Segment

The redacted version lives under [static/redacted/](src/main/resources/static/redacted/), separate from the Thymeleaf templates. It is served as static files, so it does not pass through `MonitorController` and does not automatically stay in sync with the regular pages.

- [menu.html](src/main/resources/static/redacted/menu.html) links to the static [monitor.html](src/main/resources/static/redacted/monitor.html).
- The static monitor links to the redacted portfolio and camera pages using relative paths.
- Redacted portfolios are under [static/redacted/portfolios/](src/main/resources/static/redacted/portfolios/); camera placeholders are under [static/redacted/cameras/](src/main/resources/static/redacted/cameras/).
- [redacted.css](src/main/resources/static/redacted/redacted.css) provides the blackout, blocked-photo, and blocked-media styles. Individual redacted pages combine those styles with the corresponding regular page styles where appropriate.
- The redacted Ken page, [ken.html](src/main/resources/static/redacted/portfolios/ken.html), now uses the current `.ef-*` portfolio layout while replacing personal and project details with redaction placeholders.

Because these pages are independent static copies, update a redacted page separately whenever its regular counterpart changes and the redacted version should reflect that change.
