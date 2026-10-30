function selectCamera(camName, redirectUrl) {
    document.getElementById('cam-title').innerText = camName + " - LOADING PORTFOLIO...";
    setTimeout(() => {
        window.location.href = redirectUrl;
    }, 600);
}

function triggerJumpscare(camName, message) {
    document.getElementById('cam-title').innerText = camName + " - ERROR";
    const modal = document.getElementById('jumpscare-modal');
    document.getElementById('jumpscare-text').innerText = message;
    modal.classList.remove('hidden');
}

function closeJumpscare() {
    document.getElementById('jumpscare-modal').classList.add('hidden');
}