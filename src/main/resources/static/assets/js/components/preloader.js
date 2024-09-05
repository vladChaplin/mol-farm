function preload(idSpinner, idButtonText) {
    const spinner = document.getElementById(idSpinner);
    const buttonText = document.getElementById(idButtonText);

    spinner.classList.remove('d-none');
    buttonText.textContent = 'Загрузка...';
}