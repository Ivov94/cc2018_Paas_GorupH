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

namespace Paas.GroupH.Model
{
    public class ConfigModel
    {
        public string BaseUrl { get; set; }
        public int Port { get; set; }

        public ConfigModel()
        {

        }
    }
}