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

document.addEventListener('DOMContentLoaded', () => {
  // Selektiramo sve .collapse elemente na stranici
  document.querySelectorAll('.collapse').forEach((collapseEl) => {

    // Kad se collapse otvori...
    collapseEl.addEventListener('shown.bs.collapse', () => {
      // Pronađi naslovni <div> koji cilja baš ovaj collapse
      const header = document.querySelector(`[data-bs-target="#${collapseEl.id}"]`);
      if (header) {
        const icon = header.querySelector('i.bi');
        if (icon) {
          // Zamijeni klasu iz strelice dolje u strelicu gore
          icon.classList.remove('bi-chevron-down');
          icon.classList.add('bi-chevron-up');
        }
      }
    });

    // Kad se collapse zatvori...
    collapseEl.addEventListener('hidden.bs.collapse', () => {
      const header = document.querySelector(`[data-bs-target="#${collapseEl.id}"]`);
      if (header) {
        const icon = header.querySelector('i.bi');
        if (icon) {
          // Vrati klasu strelice dolje
          icon.classList.remove('bi-chevron-up');
          icon.classList.add('bi-chevron-down');
        }
      }
    });

  });
});

// Login Animation with p5.js
let loginAnimationSketch = function(p) {
    let animationActive = false;
    
    p.setup = function() {
        let canvas = p.createCanvas(p.windowWidth, p.windowHeight);
        canvas.parent('animation-container');
        p.background(255, 0); // Transparent background
    };
    
    p.draw = function() {
        // Empty draw function - no animation needed
    };
    
    // Public methods to control animation
    p.startAnimation = function() {
        document.getElementById('animation-container').style.display = 'block';
        animationActive = true;
    };
    
    p.stopAnimation = function() {
        animationActive = false;
        setTimeout(() => {
            document.getElementById('animation-container').style.display = 'none';
        }, 500);
    };
    
    // Window resize handler
    p.windowResized = function() {
        p.resizeCanvas(p.windowWidth, p.windowHeight);
    };
};

// Initialize login animation when on login page
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    const animationContainer = document.getElementById('animation-container');
    
    if (loginForm && animationContainer) {
        // Initialize p5.js sketch
        let loginP5 = new p5(loginAnimationSketch);
        
        // Store p5 instance globally
        window.loginP5 = loginP5;
        
        // Form submission handler
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Apply anti-spam protection with custom loading text
            preventFormSpamming(this, 'button[type="submit"]', 'Prijava u tijeku...', 1500);
            
            // Show loading spinner
            document.getElementById('form-overlay').style.display = 'flex';
            
            // Start animation
            loginP5.startAnimation();
            
            // Submit the form after a short delay to show animation
            setTimeout(() => {
                this.submit();
            }, 1500);
        });
    }
});

function stopLoginAnimation() {
    if (window.loginP5) {
        window.loginP5.stopAnimation();
    }
}

/**
 * Prevents form button spamming by disabling the button and showing a loading spinner
 * @param {HTMLFormElement} form - The form element
 * @param {string} buttonSelector - CSS selector for the submit button (default: 'button[type="submit"]')
 * @param {string} loadingText - Text to display while loading (default: 'Učitavanje...')
 * @param {number} delay - Optional delay before submitting the form in ms (default: 0)
 */
function preventFormSpamming(form, buttonSelector = 'button[type="submit"]', loadingText = 'Učitavanje...', delay = 0) {
    if (!form) return;
    
    // Find the submit button
    const submitButton = form.querySelector(buttonSelector);
    if (!submitButton) return;
    
    // Store original button content for potential restoration
    if (!submitButton.dataset.originalHtml) {
        submitButton.dataset.originalHtml = submitButton.innerHTML;
    }
    
    // Disable the button
    submitButton.disabled = true;
    submitButton.classList.add('disabled');
    
    // Change button text and add spinner
    submitButton.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> ${loadingText}`;
    
    // Submit the form after optional delay
    if (delay > 0) {
        setTimeout(() => form.submit(), delay);
        return false; // Prevent default form submission
    }
    
    return true; // Allow form submission to continue
}

// Add event listeners to all forms that need spam protection
document.addEventListener('DOMContentLoaded', function() {
    // Get all forms that need spam protection (using a data attribute)
    const forms = document.querySelectorAll('form[data-prevent-spam="true"]');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            // Get loading text from data attribute or use default
            const loadingText = this.dataset.loadingText || 'Učitavanje...';
            
            // Prevent form spamming
            return preventFormSpamming(this, 'button[type="submit"]', loadingText);
        });
    });
    
    // Handle navigation buttons with anti-spam protection
    setupNavigationAntiSpam();
});

/**
 * Prevents navigation link spamming by disabling the link and showing a loading spinner
 * @param {HTMLAnchorElement} linkElement - The anchor element
 * @param {string} loadingText - Text to display while loading (default from data-loading-text or 'Učitavanje...')
 */
function preventLinkSpamming(linkElement, loadingText) {
    if (!linkElement) return;
    
    // Store original content for potential restoration
    if (!linkElement.dataset.originalHtml) {
        linkElement.dataset.originalHtml = linkElement.innerHTML;
    }
    
    // Disable the link
    linkElement.classList.add('disabled');
    linkElement.setAttribute('aria-disabled', 'true');
    
    // Change link text and add spinner
    linkElement.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> ${loadingText}`;
}

/**
 * Sets up anti-spam protection for navigation buttons
 * Looks for elements with id="backButton" or other navigation buttons that need protection
 */
function setupNavigationAntiSpam() {
    // Find navigation buttons that need anti-spam protection
    const navButtons = document.querySelectorAll('a#backButton, a[data-prevent-spam="true"]');
    
    navButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const href = this.getAttribute('href');
            const loadingText = this.getAttribute('data-loading-text') || 'Učitavanje...';
            
            // Apply anti-spam protection
            preventLinkSpamming(this, loadingText);
            
            // Navigate to the href after a short delay
            setTimeout(function() {
                window.location.href = href;
            }, 100);
        });
    });
}
