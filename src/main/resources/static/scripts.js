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
        window.formToSubmit = event.target; // Store the form globally
        const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));
        confirmationModal.show();
    }

    function confirmAndSubmitForm() {
        if (window.formToSubmit) {
            sessionStorage.setItem('showToast', 'true');
            window.formToSubmit.submit();
        }
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

document.addEventListener("DOMContentLoaded", function() {
    const saveButton = document.querySelector("button[type='submit']");
    const modalConfirmButton = document.querySelector("#confirmationModalRenewal .btn-primary");
    const form = document.querySelector("form");

    if (saveButton && modalConfirmButton && form) {
        saveButton.addEventListener("click", function(event) {
            event.preventDefault(); // Sprječava defaultno slanje forme

            if (!form.checkValidity()) {
                // Ako polja nisu popunjena, prikaži modal za upozorenje
                const validationModal = new bootstrap.Modal(document.getElementById("validationModal"));
                validationModal.show();
                form.classList.add("was-validated"); // Bootstrap validacija
                return; // Zaustavlja otvaranje modala za potvrdu
            }

            // Ako su polja ispravna, prikaži modal za potvrdu spremanja
            const confirmationModal = new bootstrap.Modal(document.getElementById("confirmationModalRenewal"));
            confirmationModal.show();
        });

        // Kada korisnik potvrdi, pošalji formu i onemogući gumb kako bi spriječio višestruko slanje
        modalConfirmButton.addEventListener("click", function() {
            modalConfirmButton.disabled = true; // Onemogućava dugme
            modalConfirmButton.innerHTML = "Spremanje..."; // Mijenja tekst gumba
            form.submit();
        });
    }
});

function showDocumentDetails(documentId) {
    const modal = new bootstrap.Modal(document.getElementById('documentDetailsModal'));
    const modalContent = document.querySelector('#documentDetailsModal .modal-content');
    
    // Load fragment content
    modalContent.innerHTML = '<div class="text-center p-5"><div class="spinner-border" role="status"><span class="visually-hidden">Loading...</span></div></div>';
    
    fetch(`/documents/${documentId}/details`)
        .then(response => response.text())
        .then(html => {
            modalContent.innerHTML = html;
            modal.show();
        })
        .catch(error => {
            modalContent.innerHTML = '<div class="modal-body text-danger">Greška pri učitavanju detalja dokumenta.</div>';
            modal.show();
        });
}
