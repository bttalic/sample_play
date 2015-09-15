/**
 * Created by benjamin on 15/09/15.
 */


$('body').on('click', 'a[data-role="delete"]', function(e){
    e.preventDefault();
    $toDelete = $(this);
    var conf = bootbox.confirm("Delete?", function(result){
        if(result != null){
            $.ajax({
                url: $toDelete.attr("href"),
                method: "delete"
            }).success(function(response){
                $toDelete.parents($toDelete.attr("data-delete-parent")).remove();
            });
        }
    });


});