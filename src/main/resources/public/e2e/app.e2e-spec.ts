import { ComptesAppPage } from './app.po';

describe('comptes-app App', function() {
  let page: ComptesAppPage;

  beforeEach(() => {
    page = new ComptesAppPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
