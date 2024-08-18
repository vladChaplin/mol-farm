function inputImageOnForm() {
    preview()
    checkFileSize()
}


function preview() {
    const fileInput = document.getElementById('photoFile');
    const frame = document.getElementById('frame');
    frame.src = URL.createObjectURL(fileInput.files[0]);
    document.getElementById('imageContainer').classList.remove('d-none');
}
    function clearImage() {
    const fileInput = document.getElementById('photoFile');
    const frame = document.getElementById('frame');
    const imageContainer = document.getElementById('imageContainer');

    fileInput.value = null;
    frame.src = "";
    imageContainer.style.display = 'none';
}

    function checkFileSize() {
    let fileInput = document.getElementById('photoFile');
    if (fileInput.files[0].size > 2097152) { // 2 MB
        alert('Файл изображения не должен превышать 2 MB!');
        fileInput.value = "";
        location.reload();
    }
}

try {
    document.getElementById('formUpdatePost').addEventListener('submit', function() {
        var submitBtn = document.getElementById('submitBtn');
        var spinner = document.getElementById('spinner');
        var buttonText = document.getElementById('buttonText');

        submitBtn.disabled = true;
        spinner.classList.remove('d-none');
        buttonText.textContent = 'Загрузка...';

    });
} catch (e) {
    console.log("Ошибка после загрузки формы! " + e)
}



