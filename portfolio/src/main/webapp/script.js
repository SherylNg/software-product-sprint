// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomQuote() {
  const quotes =
      ['Let your smile change the world, dont let the world change your smile :)',
       'The best is yet to be!',
       'You dont have to be great to start, but you have to start to be great!',
       'When you cannot find the sunshine, be the SUNSHINE!',
       'Dont stop until you are proud'];

  // Pick a random greeting.
  const quote = quotes[Math.floor(Math.random() * quotes.length)];

  // Add it to the page.
  const quoteContainer = document.getElementById('quote-container');
  quoteContainer.innerText = quote;
}
function getComments() {
  const response = await fetch('/comments');
  const json = await response.text();
  document.getElementById('comments').innerText = json;
}

window.onload = function () {
    startTab();
}

function startTab() {
    document.getElementById("defaultOpen").click();
}
    
function openPage(pageName, elmnt, color) {
  // Hide all elements with class="tabcontent" by default */
  var tabContent = document.getElementsByClassName("tabcontent");
  for (var i = 0; i < tabContent.length; i++) {
    tabContent[i].style.display = "none";
  }

  // Remove the background color of all tablinks/buttons
  var tabLinks = document.getElementsByClassName("tablink");
  for (var i = 0; i < tabLinks.length; i++) {
    tabLinks[i].style.backgroundColor = "";
  }

  // Show the specific tab content
  document.getElementById(pageName).style.display = "block";

  // Add the specific color to the button used to open the tab content
  elmnt.style.backgroundColor = color;
}
