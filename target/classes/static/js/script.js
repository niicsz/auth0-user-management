document.addEventListener('DOMContentLoaded', function() {

    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    });

    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl)
    });

    setTimeout(function() {
        var alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
        alerts.forEach(function(alert) {
            if (bootstrap.Alert) {
                var bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            } else {
                alert.style.display = 'none';
            }
        });
    }, 5000);

    document.querySelectorAll('.toggle-password').forEach(function(button) {
        button.addEventListener('click', function() {
            var input = document.querySelector(button.getAttribute('data-target'));
            if (input.type === 'password') {
                input.type = 'text';
                button.innerHTML = '<i class="bi bi-eye-slash"></i>';
            } else {
                input.type = 'password';
                button.innerHTML = '<i class="bi bi-eye"></i>';
            }
        });
    });

    document.querySelectorAll('[data-confirm]').forEach(function(element) {
        element.addEventListener('click', function(event) {
            if (!confirm(element.getAttribute('data-confirm'))) {
                event.preventDefault();
                return false;
            }
        });
    });

    var searchInput = document.getElementById('table-search');
    if (searchInput) {
        searchInput.addEventListener('keyup', function() {
            var searchTerm = searchInput.value.toLowerCase();
            var tableRows = document.querySelectorAll('.searchable-table tbody tr');

            tableRows.forEach(function(row) {
                var text = row.textContent.toLowerCase();
                if (text.indexOf(searchTerm) > -1) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
    }

    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');
        }, false);
    });

    var roleSelectors = document.querySelectorAll('.role-selector');
    if (roleSelectors.length > 0) {
        roleSelectors.forEach(function(selector) {
            selector.addEventListener('change', function() {
                var form = selector.closest('form');
                if (form) {
                    form.submit();
                }
            });
        });
    }

    var statusToggles = document.querySelectorAll('.status-toggle');
    if (statusToggles.length > 0) {
        statusToggles.forEach(function(toggle) {
            toggle.addEventListener('change', function() {
                var form = toggle.closest('form');
                if (form) {
                    form.submit();
                }
            });
        });
    }

    var userFilter = document.getElementById('user-filter');
    if (userFilter) {
        userFilter.addEventListener('change', function() {
            var filterValue = userFilter.value;
            var tableRows = document.querySelectorAll('.user-table tbody tr');

            if (filterValue === 'all') {
                tableRows.forEach(function(row) {
                    row.style.display = '';
                });
            } else {
                tableRows.forEach(function(row) {
                    var roleElements = row.querySelectorAll('.role-badge');
                    var hasRole = false;

                    roleElements.forEach(function(roleElement) {
                        if (roleElement.textContent.trim() === filterValue) {
                            hasRole = true;
                        }
                    });

                    row.style.display = hasRole ? '' : 'none';
                });
            }
        });
    }
});