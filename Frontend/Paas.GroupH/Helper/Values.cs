using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Paas.GroupH.Helper
{
    public static class Values
    {
        public const string LogTag = "GroupHLogs";
        public static string FolderPath { get; set; }

        public const string Foldername = "grouph";

        public const string DefaultHost = "http://10.0.0.99";
        public const string DefaultPort = "80";
        public const string DefaultPath = "/img";

        public static int TestIndex = -1;

    }
}