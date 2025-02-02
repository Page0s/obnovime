function showForm() {
    var selectedValue = document.getElementById("documentType").value;
    var forms = document.querySelectorAll(".form-container");
    var backButton = document.querySelector(".container > a.btn-light");

    // Sakrij sve forme
    forms.forEach(function (form) {
        form.style.display = "none";
    });

    // Prikaži samo odabranu formu
    if (selectedValue) {
        document.getElementById("form-" + selectedValue).style.display = "block";
        if (backButton) backButton.style.display = "none"; // Sakrij dodatnu tipku
    } else {
        if (backButton) backButton.style.display = "block"; // Prikazati ako nema forme
    }
}

            document.querySelectorAll('input[type="text"][id^="reminderDay"]').forEach(input => {
            input.addEventListener('input', function () {
                const warning = this.nextElementSibling;
                if (isNaN(this.value) && this.value !== '') {
                    if (!warning) {
                        const warningMessage = document.createElement('small');
                        warningMessage.textContent = 'Molimo unesite samo broj.';
                        warningMessage.style.color = 'red';
                        this.parentNode.appendChild(warningMessage);
                    }
                } else if (warning) {
                    warning.remove();
                }
            });
        });
    function openConfirmationModal(event) {
        event.preventDefault();
        formToSubmit = event.target;
        const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
        confirmationModal.show();
    }
document.addEventListener("DOMContentLoaded", function() {
    if (sessionStorage.getItem("showToast") === "true") {
        const toastEl = document.getElementById("confirmationToast");
        if (toastEl) {
            const toast = new bootstrap.Toast(toastEl, {delay: 3000});
            toast.show();
        }
        sessionStorage.removeItem("showToast"); // Briše toast nakon prikazivanja
    }
});
