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
    public enum UpdateProgress
    {
        NotStarted,
        InProcess,
        Done
    }

    public class RestImageModel
    {
        public string Id { get; set; }
        public byte[] Data { get; set; }
        public string MimeType { get; set; }
        public string Filename { get; set; }
        public DateTime Date { get; set; }
        public UpdateProgress Progress1 { get; set; }
        public UpdateProgress Progress2 { get; set; }
        public UpdateProgress Progress3 { get; set; }
        public UpdateProgress Progress4 { get; set; }
        public bool IsDone { get; set; }

        public RestImageModel()
        { }
    }
}