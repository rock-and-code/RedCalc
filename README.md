# red-calc-app

<h1>RedCalc</h1>

<p>Welcome to the "RedCalc" repository, an open-source tool designed to help users analyze real estate data from Redfin and gain valuable insights into market trends. With this tool, users can calculate the average list price by state, city, or zip code, as well as analyze rental prices and trends using the Realty Mole Property API.</p>

<p>In addition to its data analysis capabilities, the "RedCalc" app also offers an underwriting feature that enables users to make informed decisions about potential real estate investments. By inputting estimated monthly rents, vacancy rate, credit loss rate, and cap rate before debt service, users can calculate the projected net operating income (NOI) for a property, which can be used to calculate potential ROI.</p>

<h2>Installation</h2>
<ol>
  <li>Clone the repository to your local machine using git clone https://github.com/username/RedCalc.git</li>
  <li>Navigate to the RedCalc directory using cd RedCalc.</li>
  <li>Open the project in your preferred IDE or text editor.</li>
  <li>Within the com.rockandcode.redcalc.ui.App class, change the database folder path in line 65 to your desired path.</li>
  <li>Within the com.rockandcode.redcalc.util.GetRentData class, enter your Realty Mole Property API key on line number 64 to enable the app's functionality of downloading data.</li>
  <li>Create a freemium Realty Mole Property account by visiting either https://www.realtymole.com/api or https://rapidapi.com/realtymole/api/realty-mole-property-api.</li>
  <li>Build and run the application.</li>
</ol>

<h2>Usage</h2>
<h4>Import redfin's sales listings</h4>
<p>You can import Redfin's sales listings by clicking on Import > Sales listings from CSV's File or by using the keyboard shortcut Ctrl + S. This sales listings reader function is set to read CSV files from Redfin API. To download properties data from the Redfin website, please refer to the following link: <code>https://support.redfin.com/hc/en-us/articles/360016476931-Downloading-Data</code>

<h4>Import rent listings from CSV File</h4>
<p>This app allows users to import rent listings from a CSV file. For this, you can click on Import > Rent Listing from CSV File or using
the keyboard shortcut Ctrl + R. This rent listing reader function will import the rent list from a CSV file that is structured as follow:</p>
<ul>
  <li>Column 1 (A): Address (String) e.g. 2950 McKinney Ave (Must not contain comma!)</li>
  <li>Column 2 (B): Property type (String) e.g. Condo, Single-family, townhouse, etc</li>
  <li>Column 3 (C): Listing Date (String) e.g. 2023-03-20TZ</li>
  <li>Column 4 (D): Zipcode (Integer) e.g. 10010</li>
  <li>Column 5 (E): Rent (Integer) e.g. 1500</li>
  <li>Column 6 (F): Number of beds (Integer) e.g. 3</li>
  <li>Column 7 (G): Number of baths (Double) e.g. 1.5</li>
  <li>Column 8 (H): Square footage (Integer) e.g. 1200</li>
  <li>Column 9 (I): City (String) e.g. New York</li>
  <li>Column 10 (J): State (String) e.g. NY</li>
</ul>

<h4>Import Fair Market Rents</h4>
<p>This app allows users to import fair market rents from a CSV file provided by the Department of Housing and Urban Development. For this, you can click on Import > Fair market rent from CSV File or by using the keyboard shortcut Ctrl + F. This rent listing reader function will import the rent list from an FRMR's CSV file. You can download the Fair market rents xlxs file on the following link: <code>https://www.huduser.gov/portal/datasets/fmr.html#data_2023</code>  and save it as a CSV file and remove the commas in the address column (Column 1 or A). You can remove the commas by using the Find and Replace functionality
of MS Excel or your preferred spreadsheet app. The file downloaded is structured as follows</p>
<ul>
  <li>Column 1 (A): Address (String) e.g. Abilene TX MSA (Must not contain comma!) (Data on this column is ignored)</li>
  <li>Column 2 (B): HUD metropolitan area code e.g. METRO10180M10180 (Data on this column is ignored)</li>
  <li>Column 3 (C): Zipcode (Integer) e.g. 10010</li>
  <li>Column 4 (D): Studio fair rent rate (Integer)</li>
  <li>Column 5 (E): One bedroom fair rent rate (Integer)</li>
  <li>Column 6 (F): Two bedrooms  fair rent rate (Integer)</li>
  <li>Column 7 (G): Three bedrooms fair rent rate (Integer)</li>
  <li>Column 8 (H): Four or more bedrooms fair rent rate (Integer)</li>
</ul>



<h2>License</h2>

<p>RedCalc is available under the MIT license. Users are free to use, modify, and distribute the code as they see fit. We hope that this tool will be useful to anyone who is interested in analyzing real estate data and gaining valuable insights into market trends.</p>

