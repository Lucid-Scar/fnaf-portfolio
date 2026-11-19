(() => {
    const intro = document.getElementById("recruitment-intro");
    const pageShell = document.querySelector(".page-shell");
    const dossier = document.getElementById("dossier");
    const cameraLink = document.getElementById("sequence-return");
    const openButton = document.getElementById("open-bag");
    const viewFileButton = document.getElementById("view-file");
    const sequenceStatus = document.getElementById("sequence-status");
    const sequenceCaption = document.querySelector(".sequence-caption");

    if (!intro || !pageShell || !dossier || !cameraLink || !openButton || !viewFileButton || !sequenceStatus || !sequenceCaption) {
        return;
    }

    const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    let state = "sealed";

    const finishOpening = () => {
        if (state !== "opening") {
            return;
        }

        state = "open";
        intro.classList.remove("is-opening");
        intro.classList.add("is-open");
        openButton.hidden = true;
        viewFileButton.hidden = false;
        sequenceStatus.textContent = "BAG OPEN // PERSONNEL FILE READY";
        sequenceCaption.textContent = "RECRUITMENT RECORD // RECOVERED";
        viewFileButton.focus({ preventScroll: true });
    };

    const showDossier = () => {
        if (state !== "open") {
            return;
        }

        state = "closing";
        intro.classList.add("is-dismissing");
        if (!reducedMotion) {
            pageShell.hidden = false;
            dossier.classList.add("is-sliding-in");
        }

        const revealDossier = () => {
            viewFileButton.blur();
            intro.hidden = true;
            pageShell.hidden = false;
            dossier.inert = false;
            dossier.removeAttribute("aria-hidden");
            document.body.classList.remove("recruitment-active");
            document.removeEventListener("keydown", handleKeydown);
        };

        if (reducedMotion) {
            revealDossier();
        } else {
            window.setTimeout(revealDossier, 420);
        }
    };

    const openBag = () => {
        if (state !== "sealed") {
            return;
        }

        state = "opening";
        openButton.hidden = true;
        intro.classList.add("is-opening");
        sequenceStatus.textContent = "OPENING BAG // RETRIEVING FILE";
        sequenceCaption.textContent = "UNSEALING PERSONNEL BAG // RETRIEVING FILE";
        sequenceStatus.focus({ preventScroll: true });

        if (reducedMotion) {
            finishOpening();
        } else {
            window.setTimeout(finishOpening, 3000);
        }
    };

    const handleKeydown = (event) => {
        if (event.key === "Escape") {
            showDossier();
        } else if (event.key === "Tab") {
            event.preventDefault();
            const primaryControl = state === "sealed" ? openButton : state === "open" ? viewFileButton : sequenceStatus;
            const controls = [cameraLink, primaryControl];
            const activeIndex = controls.indexOf(document.activeElement);
            const nextIndex = event.shiftKey
                ? (activeIndex <= 0 ? controls.length - 1 : activeIndex - 1)
                : (activeIndex < 0 || activeIndex === controls.length - 1 ? 0 : activeIndex + 1);
            controls[nextIndex].focus();
        }
    };

    dossier.inert = true;
    dossier.setAttribute("aria-hidden", "true");
    document.body.classList.add("recruitment-active");
    intro.hidden = false;
    openButton.addEventListener("click", openBag);
    viewFileButton.addEventListener("click", showDossier);
    document.addEventListener("keydown", handleKeydown);
    openButton.focus({ preventScroll: true });
})();
