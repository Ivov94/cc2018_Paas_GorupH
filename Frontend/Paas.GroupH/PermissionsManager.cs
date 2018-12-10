using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Content.PM;
using Android.OS;
using Android.Runtime;
using Android.Support.V4.App;
using Android.Support.V4.Content;
using Android.Views;
using Android.Widget;

namespace Paas.GroupH
{
    public class PermissionsManager
    {
        private const int PERMISSIONS_REQUEST_CODE = 1001;
        private string[] _permissions;
        private Activity _activity;


        public PermissionsManager(Activity currentActivity, string[] permissions)
        {
            _activity = currentActivity;
            _permissions = permissions;
        }

        private bool IsPermissionEnabled(string name) => ContextCompat.CheckSelfPermission(_activity.ApplicationContext, name) != Permission.Granted;

        public void EnablePermissions()
        {
            var neededPermissions = _permissions.Where(p => !IsPermissionEnabled(p)).ToArray();
            ActivityCompat.RequestPermissions(_activity, neededPermissions, PERMISSIONS_REQUEST_CODE);
        }
    }
}