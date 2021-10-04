import React from 'react';

import { HeaderContainer, OptionsContainer, OptionLink} from './header.styles';

const Header = () => (
  <HeaderContainer>
    <OptionsContainer>
      <OptionLink to="/">HOMEPAGE</OptionLink>
    </OptionsContainer>


  </HeaderContainer>
);

export default Header;