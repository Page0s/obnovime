function showForm() {
    // Dohvati odabranu vrijednost iz drop-down izbornika
    const selectedValue = document.getElementById("documentType").value;

    // Sakrij sve forme
    document.querySelectorAll(".form-container").forEach(form => {
        form.style.display = "none";
    });

    // Dohvati tipku "Nazad"
    const backButton = document.querySelector("a.btn-secondary"); // Ako je <a href="/main" class="btn btn-secondary">Nazad</a>

    // Prikazivanje odgovarajuće forme i kontrola tipke "Nazad"
    if (selectedValue) {
        if (backButton) backButton.style.display = "inline-block"; // Prikaži "Nazad" samo kada je forma vidljiva
        const formToShow = document.getElementById(`form-${selectedValue}`);
        if (formToShow) {
            formToShow.style.display = "block";
        }
    } else {
        if (backButton) backButton.style.display = "none"; // Sakrij "Nazad" ako nijedna forma nije odabrana
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
