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
    public class RestProgressModel
    {
        public string ImageName { get; set; }
        public Boolean Failed { get; set; }

        public string Message { get; set; }

        public bool FilterNegativeDone {get; set;}

        public bool FilterRedDone { get; set; }

        public bool FilterGreenDone { get; set; }

        public bool FilterBlueDone { get; set; }

        public bool ImageJoinDone { get; set; }

        public RestProgressModel()
        { }
    }
}