define([
    'r3/editor/ConfirmDialog',
    'dojo/_base/Deferred',
    'dojo/aspect',
    'dojo/_base/array'
], function (ConfirmDialog, Deferred, aspect, array) {
    return {
        openInExtWindow: function (url, name, width, height) {
            if (!width) {
                width = 500;
            }
            if (!height) {
                height = 600;
            }

            if (height > screen.height) {
                height = screen.height * 0.9
            }

            if (width > screen.width) {
                width = screen.width * 0.9
            }

            var left = ((screen.width / 2) - (width / 2)) * Math.random();// * (max_width - min_width) + min_width;
            var top = ((screen.height / 2) - (height / 2)) * Math.random();// * (max_height - min_height) + min_height;

            window.open(url, name, 'width=' + width + ',height=' + height + ',scrollbars=no,toolbar=no,' +
                'screenx=' + left + ',screeny=' + top +
                'screenX=' + left + ',screenY=' + top +
                'left=' + left + ',top=' + top +
                ',location=no,titlebar=no,directories=no,status=no,menubar=no');
        },
        openSameWindow: function (url) {
            window.location = url;
        },
        confirmDialog: function (kwArgs) {
            var confirmDialog = new ConfirmDialog(kwArgs);
            confirmDialog.startup();

            var deferred = new Deferred();
            var signal, signals = [];

            var destroyDialog = function () {
                array.forEach(signals, function (signal) {
                    signal.remove();
                });
                delete signals;
                confirmDialog.destroyRecursive();
            };

            signal = aspect.after(confirmDialog, "onExecute", function () {
                destroyDialog();
                deferred.resolve('MessageBox.OK');
            });
            signals.push(signal);

            signal = aspect.after(confirmDialog, "onCancel", function () {
                destroyDialog();
                deferred.cancel('MessageBox.Cancel');
            });
            signals.push(signal);

            confirmDialog.show();
            return deferred;
        }

    };
});
