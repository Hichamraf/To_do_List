package com.hicham.settings.ui

import com.hicham.settings.ui.SettingsAction.OnDarkModeCheckChange
import com.hicham.shared.domain.usecase.GetDarKModeUseCase
import com.hicham.shared.domain.usecase.SaveDarkModeUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest{

    private val getDarKModeUseCase: GetDarKModeUseCase=mockk()
    private val saveDarkModeUseCase: SaveDarkModeUseCase= mockk()
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp(){
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `test on viewModel init loads if dark mode is enabled and assign it to state`(){
        coEvery { getDarKModeUseCase.invoke(Unit) } returns true
        viewModel= SettingsViewModel(getDarKModeUseCase,saveDarkModeUseCase)

        coVerify(exactly = 1) { getDarKModeUseCase.invoke(Unit) }
        assertTrue(viewModel.viewState.value.isDarkModeEnabled)
    }

    @Test
    fun `test OnDarkModeCheckChange updates the value in the shared Prefs and state`(){
        val isDark=false
        coEvery { getDarKModeUseCase.invoke(Unit) } returns true
        viewModel= SettingsViewModel(getDarKModeUseCase,saveDarkModeUseCase)
        viewModel.processViewActions(OnDarkModeCheckChange(isDark))

        coVerify(exactly = 1) { saveDarkModeUseCase.invoke(isDark) }
        assertFalse(viewModel.viewState.value.isDarkModeEnabled)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

}