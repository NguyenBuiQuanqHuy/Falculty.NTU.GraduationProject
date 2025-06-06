function showTab(event, tabId) {
          document.querySelectorAll('.tab-content').forEach(content => {
              content.classList.remove('active');
          });
          document.querySelectorAll('.tab-item').forEach(tab => {
              tab.classList.remove('active');
          });
          document.getElementById(tabId).classList.add('active');
          event.target.classList.add('active');
      }