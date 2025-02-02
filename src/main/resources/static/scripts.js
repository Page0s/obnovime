function showForm() {
    var selectedValue = document.getElementById("documentType").value;
    var forms = document.querySelectorAll(".form-container");
    var backButton = document.querySelector(".container > a.btn-secondary");

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
        function openConfirmationModalRenewal() {
            const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModalRenewal'));
            confirmationModal.show();
        }

        function submitForm() {
            sessionStorage.setItem('showToast', 'true');
            document.getElementById('renewalForm').submit();
        }
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
 function validateReminder() {
            let reminderField = document.getElementById("renewalPeriod");
            let errorMsg = document.getElementById("error-message");

            if (isNaN(reminderField.value) || reminderField.value.trim() === "") {
                errorMsg.style.display = "block";
                return false;
            } else {
                errorMsg.style.display = "none";
                return true;
            }
        }

        function showConfirmationModal(event) {
            event.preventDefault(); // Spriječi automatsko slanje forme
            if (validateReminder()) {
                let modal = new bootstrap.Modal(document.getElementById('confirmationModalEdit'));
                modal.show();
            }
        }

        function submitFormEdit() {
            sessionStorage.setItem('showToast', 'true');
            document.getElementById('editForm').submit();
        }
