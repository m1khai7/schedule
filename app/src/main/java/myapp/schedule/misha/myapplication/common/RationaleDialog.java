package myapp.schedule.misha.myapplication.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A dialog that explains the use of the location permission and requests the necessary
 * permission. The activity should implement
 * {@link androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback}
 * to handle permit or denial of this permission request.
 */
public class RationaleDialog extends DialogFragment {

    /**
     * Argument permission request code.
     */
    private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";
    /**
     * Argument permission.
     */
    private static final String PERMISSION = "permission";
    /**
     * Argument message.
     */
    private static final String MESSAGE = "message";

    /**
     * Creates a new instance of a dialog displaying the rationale for the use of the location
     * permission.
     * The permission is requested after clicking 'ok'.
     *
     * @param requestCode Id of the request that is used to request the permission.
     * @param permission  permission
     * @param message     message
     * @return dialog instance
     */
    public static RationaleDialog newInstance(int requestCode, String permission,
                                              String message) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
        arguments.putString(PERMISSION, permission);
        arguments.putString(MESSAGE, message);
        RationaleDialog dialog = new RationaleDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
        final String permission = arguments.getString(PERMISSION);
        return new AlertDialog.Builder(getActivity())
                .setMessage(getArguments().getString(MESSAGE))
                .setPositiveButton(android.R.string.ok, (dialog, which)
                        -> ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode))
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }
}
