<select id="hours"></select>
<select id="minutes"></select>

<script>

       function createOption(value, text) {
              var option = document.createElement('option');
              option.text = text;
              option.value = value;
              return option;
       }

       var hourSelect = document.getElementById('hours');
       for(var i = 8; i <= 18; i++){
              hourSelect.add(createOption(i, i));
       }

       var minutesSelect = document.getElementById('minutes');
       for(var i = 0; i < 60; i += 15) {
              minutesSelect.add(createOption(i, i));
       }
</script>