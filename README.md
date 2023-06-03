# red-calc-app

<h1>RedCalc</h1>

<p>Welcome to the "RedCalc" repository, an open-source tool designed to help users analyze real estate data from Redfin and gain valuable insights into market trends. With this tool, users can calculate the average list price by state, city, or zip code, as well as analyze rental prices and trends using the Realty Mole Property API.</p>

<p>In addition to its data analysis capabilities, the "RedCalc" app also offers an underwriting feature that enables users to make informed decisions about potential real estate investments. By inputting estimated monthly rents, vacancy rate, credit loss rate, and cap rate before debt service, users can calculate the projected net operating income (NOI) for a property, which can be used to calculate potential ROI.</p>

<h2>Installation</h2>
<ol>
  <li>Clone the repository to your local machine using git clone https://github.com/username/RedCalc.git</li>
  <li>Navigate to the RedCalc directory using cd RedCalc.</li>
  <li>Open the project in your preferred IDE or text editor.</li>
  <li>Within the com.rockandcode.redcalc.model.App class, change the database folder path after line number 75 to your desired path.</li>
  <li>Within the com.rockandcode.redcalc.util.GetRentData class, enter your Realty Mole Property API key on line number 65 to enable the app's  functionality of downloading data.</li>
  <li>Create a freemium Realty Mole Property account by visiting either https://www.realtymole.com/api or https://rapidapi.com/realtymole/api/realty-mole-property-api.</li>
  <li>Build and run the application.</li>
</ol>

<h2>License</h2>

<p>RedCalc is available under the MIT license. Users are free to use, modify, and distribute the code as they see fit. We hope that this tool will be useful to anyone who is interested in analyzing real estate data and gaining valuable insights into market trends.</p>

