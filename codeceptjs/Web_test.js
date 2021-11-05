Feature('Simple Feature');

Scenario('test something', ({ I }) => {
  I.amOnPage('https://admhelp.microfocus.com/uftmobile/en/latest/Content/Resources/_TopNav/_TopNav_Home.htm');
  I.see('UFT Mobile Help');
});

